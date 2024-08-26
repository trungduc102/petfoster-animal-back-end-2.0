package com.poly.petfoster.service.address;

import com.poly.petfoster.request.addresses.AddressUserRequest;
import com.poly.petfoster.response.ApiResponse;

public interface AddressService {
    ApiResponse getDefaultAddress(String token);

    ApiResponse getUserAddresses(String username);

    ApiResponse getAddressByToken(String token);

    ApiResponse getAddressById(String token, Integer id);

    ApiResponse create(String token, AddressUserRequest data);

    ApiResponse update(String token, Integer id, AddressUserRequest data);

    ApiResponse delete(String token, Integer id);

}
