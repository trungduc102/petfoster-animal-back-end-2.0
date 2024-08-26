package com.poly.petfoster.controller.review;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.review.ReviewRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.review.ReviewService;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    @Autowired
    ReviewService reviewSerivce;
    
    @PostMapping("user/reviews")
    public ResponseEntity<ApiResponse> order(@RequestHeader("Authorization") String jwt, @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewSerivce.createReview(jwt, reviewRequest));
    }
}
