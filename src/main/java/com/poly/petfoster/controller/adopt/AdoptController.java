package com.poly.petfoster.controller.adopt;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.adopts.AdoptsRequest;
import com.poly.petfoster.request.adopts.CancelAdoptRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.adopt.AdoptService;

@RestController
@RequestMapping("/api/user/adopts")
public class AdoptController {

    @Autowired
    AdoptService adoptService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAdopts(@RequestHeader("Authorization") String jwt, Optional<Integer> page,
            Optional<String> status) {
        return ResponseEntity.ok(adoptService.getAdopts(jwt, page, status));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> adopt(@RequestHeader("Authorization") String jwt,
            @Valid @RequestBody AdoptsRequest adoptsRequest) {
        return ResponseEntity.ok(adoptService.adopt(jwt, adoptsRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> cancelAdoptByUser(@PathVariable Integer id,
            @RequestHeader("Authorization") String jwt, @Valid @RequestBody CancelAdoptRequest cancelAdoptRequest) {
        return ResponseEntity.ok(adoptService.cancelAdoptByUser(id, jwt, cancelAdoptRequest));
    }

}
