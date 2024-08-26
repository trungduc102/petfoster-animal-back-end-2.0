package com.poly.petfoster.service;

import java.util.Optional;

import com.poly.petfoster.response.ApiResponse;

public interface TakeActionService {
    ApiResponse homePageTakeAction();
    ApiResponse bestSellers(Optional<Integer> page);
}
