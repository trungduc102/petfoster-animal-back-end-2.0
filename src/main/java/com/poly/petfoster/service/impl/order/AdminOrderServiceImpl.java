package com.poly.petfoster.service.impl.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.constant.OrderStatus;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.Payment;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.PaymentRepository;
import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.order_history.OrderFilterResponse;
import com.poly.petfoster.service.order.AdminOrderService;
import com.poly.petfoster.ultils.FormatUtils;
import com.poly.petfoster.ultils.GiaoHangNhanhUltils;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    GiaoHangNhanhUltils giaoHangNhanhUltils;

    @Autowired
    OrderSeviceImpl orderSeviceImpl;

    @Autowired
    FormatUtils formatUtils;

    @Override
    public ApiResponse updateOrderStatus(Integer id, UpdateStatusRequest updateStatusRequest) {

        Orders order = ordersRepository.findById(id).orElse(null);
        if (order == null) {
            return ApiResponse.builder()
                    .message("order not found")
                    .status(404)
                    .errors("order not found")
                    .build();
        }

        if (order.getStatus().equalsIgnoreCase(OrderStatus.CANCELLED_BY_ADMIN.getValue())
                || order.getStatus().equalsIgnoreCase(OrderStatus.DELIVERED.getValue())
                || order.getStatus().equalsIgnoreCase(OrderStatus.CANCELLED_BY_CUSTOMER.getValue())) {
            return ApiResponse.builder()
                    .message("Cannot update the order has been delivered or cancelled")
                    .status(HttpStatus.FAILED_DEPENDENCY.value())
                    .errors("Cannot update the order has been delivered or cancelled")
                    .build();
        }

        String updateStatus;
        try {
            updateStatus = OrderStatus.valueOf(updateStatusRequest.getStatus()).getValue();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message(updateStatusRequest.getStatus() + " doesn't exists in the enum")
                    .status(404)
                    .errors(updateStatusRequest.getStatus() + " doesn't exists in the enum")
                    .build();
        }

        if (updateStatus.equalsIgnoreCase(OrderStatus.PLACED.getValue())) {
            return ApiResponse.builder()
                    .message("Cannot update back the status")
                    .status(HttpStatus.CONFLICT.value())
                    .errors("Cannot update back the status")
                    .build();
        }

        if (updateStatus.equalsIgnoreCase(OrderStatus.DELIVERED.getValue())
                && !order.getStatus().equalsIgnoreCase(OrderStatus.SHIPPING.getValue())) {
            return ApiResponse.builder()
                    .message("Please update this order to Shipping status first")
                    .status(HttpStatus.CONFLICT.value())
                    .errors("Cannot jump update the status")
                    .build();
        }

        if (updateStatus.equalsIgnoreCase(OrderStatus.CANCELLED_BY_CUSTOMER.getValue())) {
            return ApiResponse.builder()
                    .message("Please choose Cancelled By Admin")
                    .status(HttpStatus.CONFLICT.value())
                    .errors("Admin cannot choose Cancelled By Customer")
                    .build();
        }

        order.setStatus(updateStatus);
        order.setDescriptions(updateStatusRequest.getReason() != null ? updateStatusRequest.getReason() : "");
        ordersRepository.save(order);

        Payment payment = order.getPayment();
        if (updateStatus.equalsIgnoreCase(OrderStatus.DELIVERED.getValue())) {
            if (payment.getPaymentMethod().getId() == 1) {
                payment.setIsPaid(true);
                payment.setPayAt(new Date());
                paymentRepository.save(order.getPayment());
            }
        }

        if (updateStatus.equalsIgnoreCase(OrderStatus.CANCELLED_BY_ADMIN.getValue()) && order.getGhnCode() != null) {

            List<String> order_codes = new ArrayList<>();
            RestTemplate restTemplate = new RestTemplate();

            order_codes.add(order.getGhnCode());
            HttpEntity<Map<String, Object>> request = giaoHangNhanhUltils.createRequest("order_codes", order_codes);
            ResponseEntity<String> response = restTemplate.postForEntity(Constant.GHN_CANCEL, request, String.class);

            // return quantity
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                orderSeviceImpl.returnQuantity(orderDetail.getProductRepo(), orderDetail.getQuantity());
            }
        }

        return ApiResponse.builder()
                .message("Successfully")
                .status(200)
                .errors(false)
                .build();
    }

    @Override
    public ApiResponse updateReadForOrder(Integer id) {

        if (id == null) {
            return ApiResponse.builder()
                    .message("ID invalid !")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        Orders order = ordersRepository.findById(id).orElse(null);

        if (order == null) {
            return ApiResponse.builder()
                    .message("Not found !")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        order.setRead(true);

        ordersRepository.save(order);

        return ApiResponse.builder()
                .message("Successfully!!!")
                .status(200)
                .errors(false)
                .data(OrderFilterResponse.builder()
                        .orderId(order.getId())
                        .username(order.getUser().getUsername())
                        .total(order.getTotal().intValue())
                        .placedDate(formatUtils.dateToString(order.getCreateAt(), "yyyy-MM-dd"))
                        .status(order.getStatus())
                        .read(order.getRead())
                        .print(order.getPrint())
                        .token(order.getGhnCode())
                        .build())
                .build();
    }

    @Override
    public ApiResponse updatePrintForOrder(Integer id) {
        if (id == null) {
            return ApiResponse.builder()
                    .message("ID invalid !")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        Orders order = ordersRepository.findById(id).orElse(null);

        if (order == null) {
            return ApiResponse.builder()
                    .message("Not found !")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        order.setPrint(order.getPrint() + 1);

        ordersRepository.save(order);

        return ApiResponse.builder()
                .message("Successfully!!!")
                .status(200)
                .errors(false)
                .data(OrderFilterResponse.builder()
                        .orderId(order.getId())
                        .username(order.getUser().getUsername())
                        .total(order.getTotal().intValue())
                        .placedDate(formatUtils.dateToString(order.getCreateAt(), "yyyy-MM-dd"))
                        .status(order.getStatus())
                        .read(order.getRead())
                        .print(order.getPrint())
                        .token(order.getGhnCode())
                        .build())
                .build();
    }

}
