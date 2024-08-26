package com.poly.petfoster.service.admin.price_changes;

import com.poly.petfoster.response.ApiResponse;

public interface PriceChangeService {
    ApiResponse getPriceChange(String idProduct);
}
