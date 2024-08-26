package com.poly.petfoster.service.seach_history;

import com.poly.petfoster.response.ApiResponse;

public interface SearchHistoryService {
    ApiResponse getSeachHistopry(String jwt);
    ApiResponse updateSeachHistopry(String jwt, String keyword);
    ApiResponse deleteSeachHistopry(String jwt, String keyword);

}
