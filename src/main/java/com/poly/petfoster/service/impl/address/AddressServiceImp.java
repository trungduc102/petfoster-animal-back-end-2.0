package com.poly.petfoster.service.impl.address;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Addresses;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.AddressRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.addresses.AddressUserRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.addresses.AddressResponse;
import com.poly.petfoster.response.addresses.AddressUserResponse;
import com.poly.petfoster.service.address.AddressService;

@Service
public class AddressServiceImp implements AddressService {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    private String getUsername(String token) {
        return jwtProvider.getUsernameFromToken(token);
    }

    private AddressUserResponse buildAddressResponse(Addresses addresses) {

        if (addresses == null)
            return null;

        return AddressUserResponse.builder()
                .id(addresses.getId())
                .name(addresses.getRecipient())
                .phone(addresses.getPhone())
                .address(
                        AddressResponse.builder()
                                .province(addresses.getProvince())
                                .district(addresses.getDistrict())
                                .ward(addresses.getWard())
                                .address(addresses.getAddress())
                                .build())
                .isDefault(addresses.getIsDefault())
                .build();
    }

    private Addresses builAddresses(AddressUserRequest addressUserRequest, User user) {

        if (addressUserRequest == null) {
            return null;
        }

        boolean isDefault = addressUserRequest.isSetDefault();

        System.out.println(addressUserRequest.isSetDefault());

        List<Addresses> list = addressRepository.findByUser(user);

        Addresses addressesDefault = addressRepository.findByIsDefaultWithUser(user.getUsername());

        if (addressesDefault != null && isDefault) {
            addressesDefault.setIsDefault(false);
            addressRepository.save(addressesDefault);

        }

        if (list.isEmpty() || list.size() <= 0) {
            isDefault = true;
        }

        return Addresses.builder()
                .isDefault(isDefault)
                .address(addressUserRequest.getAddress().getAddress())
                .district(addressUserRequest.getAddress().getDistrict())
                .phone(addressUserRequest.getPhone())
                .province(addressUserRequest.getAddress().getProvince())
                .ward(addressUserRequest.getAddress().getWard())
                .recipient(addressUserRequest.getName())
                .user(user)
                .build();
    }

    @Override
    public ApiResponse getDefaultAddress(String token) {
        String username = getUsername(token);

        if (username == null || username.isEmpty()) {
            return ApiResponse.builder()
                    .message("Please login to use !")
                    .status(400)
                    .errors(true)
                    .data(null)
                    .build();
        }

        Addresses addresses = addressRepository.findByIsDefaultWithUser(username);

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(buildAddressResponse(addresses))
                .build();
    }

    @Override
    public ApiResponse getAddressByToken(String token) {
        String username = getUsername(token);

        if (username == null || username.isEmpty()) {
            return ApiResponse.builder()
                    .message("Please login to use !")
                    .status(400)
                    .errors(true)
                    .data(null)
                    .build();
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<Addresses> addresses = addressRepository.findByUser(user);

        List<AddressUserResponse> addressUserResponses = new ArrayList<>();

        addresses.stream().forEach(item -> {
            addressUserResponses.add(buildAddressResponse(item));
        });

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(addressUserResponses)
                .build();
    }

    @Override
    public ApiResponse create(String token, AddressUserRequest data) {

        String username = getUsername(token);

        if (username == null || username.isEmpty()) {
            return ApiResponse.builder()
                    .message("Please login to use !")
                    .status(400)
                    .errors(true)
                    .data(null)
                    .build();
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        int conditon = 4;

        if (addressRepository.findByUser(user).size() >= conditon) {
            return ApiResponse.builder()
                    .message("Can't add new address, becase The number of addresses exceeds the specified limit ( " + 4
                            + " ) ")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        Addresses addresses = builAddresses(data, user);

        if (addresses == null) {
            return ApiResponse.builder()
                    .message(RespMessage.INVALID.getValue())
                    .status(405)
                    .errors(true)
                    .data(null)
                    .build();
        }

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(addressRepository.save(addresses))
                .build();
    }

    @Override
    public ApiResponse update(String token, Integer id, AddressUserRequest data) {
        String username = getUsername(token);

        if (username == null || username.isEmpty()) {
            return ApiResponse.builder()
                    .message("Please login to use !")
                    .status(400)
                    .errors(true)
                    .data(null)
                    .build();
        }

        Addresses curAddresses = addressRepository.findById(id).orElse(null);

        if (curAddresses == null) {
            return ApiResponse.builder()
                    .message("Can't found item " + id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        Addresses addresses = builAddresses(data, user);

        if (addresses == null) {
            return ApiResponse.builder()
                    .message(RespMessage.INVALID.getValue())
                    .status(405)
                    .errors(true)
                    .data(null)
                    .build();
        }

        curAddresses.setProvince(addresses.getProvince());
        curAddresses.setDistrict(addresses.getDistrict());
        curAddresses.setWard(addresses.getWard());
        curAddresses.setAddress(addresses.getAddress());
        curAddresses.setIsDefault(curAddresses.getIsDefault() ? true : addresses.getIsDefault());
        curAddresses.setPhone(addresses.getPhone());
        curAddresses.setRecipient(addresses.getRecipient());

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(buildAddressResponse(addressRepository.save(curAddresses)))
                .build();
    }

    @Override
    public ApiResponse delete(String token, Integer id) {

        String username = getUsername(token);

        if (username == null || username.isEmpty()) {
            return ApiResponse.builder()
                    .message("Please login to use !")
                    .status(400)
                    .errors(true)
                    .data(null)
                    .build();
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        Addresses addresses = addressRepository.findById(id).orElse(null);

        if (addresses == null) {
            return ApiResponse.builder()
                    .message("Can't found item " + id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        addressRepository.delete(addresses);

        if (addresses.getIsDefault()) {

            List<Addresses> list = addressRepository.findByUser(user);

            if (list.size() > 0) {

                Addresses radomAddresses = list.get(0);

                if (radomAddresses != null) {
                    radomAddresses.setIsDefault(true);
                    addressRepository.save(radomAddresses);
                }
            }

        }

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(addresses)
                .build();
    }

    @Override
    public ApiResponse getAddressById(String token, Integer id) {
        String username = getUsername(token);

        if (username == null || username.isEmpty()) {
            return ApiResponse.builder()
                    .message("Please login to use !")
                    .status(400)
                    .errors(true)
                    .data(null)
                    .build();
        }

        Addresses addresses = addressRepository.findByIdAndUser(id, username);

        if (addresses == null) {
            return ApiResponse.builder()
                    .message("Can't found item " + id)
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(buildAddressResponse(addresses))
                .build();
    }

    @Override
    public ApiResponse getUserAddresses(String username) {
        User u = userRepository.findByUsername(username).orElse(null);
        if (u == null) {
            return ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Not find this username!")
                    .errors(Boolean.TRUE)
                    .data(new ArrayList<>()).build();
        }

        List<Addresses> list = addressRepository.findByUser(u);
        List<AddressUserResponse> lists = new ArrayList<>();
        for (Addresses a : list) {
            lists.add(new AddressUserResponse(a.getId(), a.getUser().getFullname(),
                    a.getPhone(), new AddressResponse(a.getProvince(), a.getDistrict(), a.getWard(), a.getAddress()),
                    a.getIsDefault()));
        }
        return ApiResponse.builder().status(HttpStatus.OK.value()).message("Successfully!").errors(Boolean.FALSE)
                .data(lists).build();
    }

}
