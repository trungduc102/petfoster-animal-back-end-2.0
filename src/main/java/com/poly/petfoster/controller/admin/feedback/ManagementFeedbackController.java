package com.poly.petfoster.controller.admin.feedback;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.feedback.FeedbackService;

@RestController
@RequestMapping("/api/admin/feedbacks")
public class ManagementFeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<ApiResponse> getFeedback(
            @RequestParam(value = "page", defaultValue = "0", required = false) Optional<Integer> page) {
        return ResponseEntity.ok(feedbackService.getFeedback(page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> seen(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(feedbackService.seen(id));
    }
}
