package com.poly.petfoster.service.recent_view_products;

import com.poly.petfoster.response.ApiResponse;

public interface RecentViewService {
    public ApiResponse getRecentView(String jwt);

    public ApiResponse putRecentView(String jwt, String producrId);
}
