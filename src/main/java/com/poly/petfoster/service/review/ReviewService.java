package com.poly.petfoster.service.review;

import com.poly.petfoster.request.review.ReviewRequest;
import com.poly.petfoster.response.ApiResponse;

public interface ReviewService {
    public ApiResponse createReview(String jwt, ReviewRequest reviewRequest);
}
