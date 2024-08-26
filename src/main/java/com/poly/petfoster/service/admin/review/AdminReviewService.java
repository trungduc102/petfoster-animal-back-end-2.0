package com.poly.petfoster.service.admin.review;

import java.util.Optional;

import com.poly.petfoster.request.review.ReviewReplayRequest;
import com.poly.petfoster.response.ApiResponse;

public interface AdminReviewService {

    ApiResponse filterReviews(Optional<String> name, Optional<Integer> minStar, Optional<Integer> maxStar,
            Optional<String> sort, Optional<Integer> page);

    ApiResponse reviewDetails(String productId);

    ApiResponse reviewDetailsFilter(String productId, Optional<Boolean> notReply);

    ApiResponse replay(String token, ReviewReplayRequest replayRequest);

    ApiResponse delete(Integer id);

}
