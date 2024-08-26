package com.poly.petfoster.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pages.HomePageService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/home-pages")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getMethodName() {
        return ResponseEntity.ok(homePageService.homepage());
    }

}
