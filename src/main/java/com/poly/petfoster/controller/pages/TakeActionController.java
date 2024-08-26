package com.poly.petfoster.controller.pages;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.TakeActionService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/take-action")
public class TakeActionController {

    @Autowired
    private TakeActionService takeActionService;

    @GetMapping()
    public ResponseEntity<ApiResponse> homePageTakeAction() {

        return ResponseEntity.ok(takeActionService.homePageTakeAction());

    }

    @GetMapping("/best-sellers")
    public ResponseEntity<ApiResponse> getBestSellers(@RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(takeActionService.bestSellers(page));

    }

}
