package com.poly.petfoster.controller.feedback;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.feedback.FeedBackRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.feedback.FeedbackService;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> feedback(@Valid @RequestBody FeedBackRequest feedBackRequest,
            HttpServletRequest request) {
        return ResponseEntity.ok(feedbackService.feedback(request, feedBackRequest));
    }
}
