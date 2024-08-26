package com.poly.petfoster.service.carts;

import java.util.List;

import com.poly.petfoster.request.carts.CartRequest;
import com.poly.petfoster.response.ApiResponse;

public interface CartService {
    ApiResponse getCarts(String jwt);

    ApiResponse updateCarts(String jwt, List<CartRequest> cartRequests);

    ApiResponse createCarts(String jwt, CartRequest cartRequest);
}
