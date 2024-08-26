package com.poly.petfoster.controller.recent_view_product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.recent_view_products.RecentViewService;

@RestController
@RequestMapping("/api/user/recent-views")
public class RecentViewController {
    @Autowired
    RecentViewService recentViewService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getRecentView(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(recentViewService.getRecentView(jwt));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> putRecentView(@RequestHeader("Authorization") String jwt,
            @PathVariable("productId") String productId) {
        return ResponseEntity.ok(recentViewService.putRecentView(jwt, productId));
    }
}
