package com.poly.petfoster.ultils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.ShippingInfo;
import com.poly.petfoster.repository.AddressRepository;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.ShippingInfoRepository;
import com.poly.petfoster.request.shipping.GHNPostRequest;
import com.poly.petfoster.request.shipping.ShippingProductRequest;
import com.poly.petfoster.response.ApiResponse;

@Component
public class GiaoHangNhanhUltils {

    @Autowired
    FormatUtils formatUtils;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ShippingInfoRepository shippingInfoRepository;

    public ApiResponse create(Orders orderRequest) {

        ShippingInfo shippingInfo = orderRequest.getShippingInfo();

        // create a items object
        ArrayList<ShippingProductRequest> list = new ArrayList<>();
        Integer totalWeight = 0;
        for (OrderDetail item : orderRequest.getOrderDetails()) {
            list.add(ShippingProductRequest.builder()
                    .name(item.getProductRepo().getProduct().getName())
                    .code(item.getProductRepo().getProduct().getId())
                    .quantity(item.getQuantity())
                    .weight(item.getProductRepo().getSize())
                    .price(item.getPrice().intValue())
                    .build());

            totalWeight += item.getQuantity() * item.getProductRepo().getSize();
        }

        Integer provinceId = getProvinceID(shippingInfo.getProvince());
        if (provinceId == null) {
            return ApiResponse.builder().message("Province name not found").status(404).errors(true).build();
        }

        Integer districtId = this.getDistrictId(provinceId, shippingInfo.getDistrict());
        if (districtId == null) {
            return ApiResponse.builder().message("District name not found").status(404).errors(true).build();
        }

        String wardId = this.getWardId(shippingInfo.getWard(), districtId);
        if (wardId == null) {
            return ApiResponse.builder().message("Ward name not found").status(404).errors(true).build();
        }

        // create a post object
        GHNPostRequest post = GHNPostRequest.builder()
                .to_name(shippingInfo.getFullName())
                .to_phone(shippingInfo.getPhone())
                .to_address(shippingInfo.getAddress())
                .to_ward_code(wardId)
                .to_district_id(districtId)
                .payment_type_id(orderRequest.getPayment().getId() == 2 ? 1 : 2)
                .cod_amount(orderRequest.getPayment().getId() == 2 ? 0 : orderRequest.getTotal().intValue())
                .items(list)
                .weight(totalWeight)
                .build();

        // request url
        String url = Constant.GHN_CREATE;

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Token", Constant.GHN_TOKEN);
        headers.set("ShopId", Constant.GHN_SHOPID);

        // build the request
        HttpEntity<GHNPostRequest> request = new HttpEntity<>(post, headers);

        ResponseEntity<String> response = null;
        try {
                // send POST request
            response = restTemplate.postForEntity(url,
                    request, String.class);
        } catch (HttpClientErrorException.BadRequest e) {
            return ApiResponse.builder().message("This province is not support").status(400).errors(true).build();
        } catch (HttpClientErrorException e) {
            // Handle other HttpClientErrorException types if needed
            System.out.println("Caught an HttpClientErrorException: " + e.getMessage());
        } catch (Exception e) {
            // Handle other types of exceptions
            System.out.println("Caught an exception: " + e.getMessage());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Integer code = null; 
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String expectedDeliveryTime = jsonNode.path("data").path("expected_delivery_time").asText();
            String ghnCode = jsonNode.path("data").path("order_code").asText();
            // code = jsonNode.path("data").path("code").asInt();

            orderRequest.setExpectedDeliveryTime(expectedDeliveryTime);
            orderRequest.setGhnCode(ghnCode);
            ordersRepository.save(orderRequest);
        } catch (Exception e) {
            e.getMessage();
        }

        return ApiResponse.builder().message("Successfully").status(200).errors(false).data(code).build();
    }

    public Integer getProvinceID(String provinceName) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> request = this.createRequest("ProvinceName", provinceName);

        ResponseEntity<String> response = restTemplate.exchange(Constant.GHN_GETPROVINCE, HttpMethod.GET, request,
                String.class);

        org.json.JSONArray dataArray = this.getData(response);
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject object = dataArray.getJSONObject(i);

            List<Object> names;
            try {
                names = object.getJSONArray("NameExtension").toList();
            } catch (Exception e) {
                continue;
            }
            
            if(object.getString("ProvinceName").equalsIgnoreCase(provinceName)) {
                return object.getInt("ProvinceID");
            }
            
            for(Object name : names) {
                if(name.toString().equalsIgnoreCase(provinceName)) {
                    return object.getInt("ProvinceID");
                }
            }
        }

        return null;
    }

    public Integer getDistrictId(Integer provinceId, String districtName) {
        
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Map<String, Object>> request = this.createRequest("province_id", provinceId);
        ResponseEntity<String> response = restTemplate.exchange(Constant.GHN_GETDISCTRICT, HttpMethod.POST, request,
                String.class);

        org.json.JSONArray dataArray = this.getData(response);
        
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject object = dataArray.getJSONObject(i);
            
            List<Object> names;
            try {
                names = object.getJSONArray("NameExtension").toList();
            } catch (Exception e) {
                continue;
            }

            if(object.getString("DistrictName").equalsIgnoreCase(districtName)) {
                return object.getInt("DistrictID");
            }
            
           for(Object name : names) {
                if(name.toString().equalsIgnoreCase(districtName)) {
                    return object.getInt("DistrictID");
                }
            }
        }

        return null;
    }

    public String getWardId(String wardName, Integer districtId) {

        // build a request
        HttpEntity<Map<String, Object>> request = this.createRequest("district_id", districtId);

        RestTemplate restTemplate = new RestTemplate();

        // send request
        ResponseEntity<String> response = restTemplate.exchange(Constant.GHN_GETWARD, HttpMethod.POST, request,
                String.class);

        // get data
        org.json.JSONArray data = this.getData(response);
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);

            List<Object> names;
            try {
                names = object.getJSONArray("NameExtension").toList();
            } catch (Exception e) {
                continue;
            }

            if(object.getString("WardName").equalsIgnoreCase(wardName)) {
                return object.getString("WardCode");
            }

            for(Object name : names) {
                if(name.toString().equalsIgnoreCase(wardName)) {
                    return object.getString("WardCode");
                }
            }
        }

        return null;
    }

    public HttpEntity<Map<String, Object>> createRequest(String key, Object value) {

        // create header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Token", Constant.GHN_TOKEN);
        headers.set("ShopId", Constant.GHN_SHOPID);

        // create a map for post parameters
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        return new HttpEntity<>(map, headers);
    }

    public org.json.JSONArray getData(ResponseEntity<String> response) {

        String responseBody = response.getBody();
        JSONObject jsonObject = null;
        if (responseBody != null) {
            jsonObject = new JSONObject(responseBody);
        }

        return jsonObject.getJSONArray("data");
    }

}
