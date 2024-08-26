package com.poly.petfoster.controller.search_history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.seach_history.SearchHistoryService;

@RestController
@RequestMapping("/api/user/search-histories")
public class HistoryController {
    @Autowired
    SearchHistoryService searchHistoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getSeachHistopry(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(searchHistoryService.getSeachHistopry(jwt));
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse> updateSeachHistopry(@RequestHeader("Authorization") String jwt,
            @RequestParam String keyword) {
        return ResponseEntity.ok(searchHistoryService.updateSeachHistopry(jwt, keyword));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse> deleteSeachHistopry(@RequestHeader("Authorization") String jwt,
            @RequestParam String keyword) {
        return ResponseEntity.ok(searchHistoryService.deleteSeachHistopry(jwt, keyword));
    }

}
