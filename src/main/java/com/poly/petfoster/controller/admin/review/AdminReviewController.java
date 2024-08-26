package com.poly.petfoster.controller.admin.review;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.review.ReviewReplayRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.review.AdminReviewService;

@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

    @Autowired
    AdminReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> filterReviews(
            @RequestParam("productName") Optional<String> productName,
            @RequestParam("minStar") Optional<Integer> minStar,
            @RequestParam("maxStar") Optional<Integer> maxStar,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(reviewService.filterReviews(productName, minStar, maxStar, sort, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> reviewDetails(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.reviewDetails(id));
    }

    @GetMapping("/details")
    public ResponseEntity<ApiResponse> reviewDetailsFilter(@RequestParam("id") String id,
            @RequestParam("notReply") Optional<Boolean> notReply) {
        return ResponseEntity.ok(reviewService.reviewDetailsFilter(id, notReply));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> replay(@RequestHeader("Authorization") String token,
            @RequestBody ReviewReplayRequest replayRequest) {
        return ResponseEntity.ok(reviewService.replay(token, replayRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(reviewService.delete(id));
    }

}
