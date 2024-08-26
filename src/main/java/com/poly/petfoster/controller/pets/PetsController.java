package com.poly.petfoster.controller.pets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pets.PetService;

@RestController
@RequestMapping("/api/user/pets")
public class PetsController {

    @Autowired
    private PetService petService;

    // id - id pet favorite

    @PutMapping("/favorite/{id}")
    public ResponseEntity<ApiResponse> favorite(@PathVariable("id") String id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(petService.favorite(id, token));
    }

    @GetMapping("/favorites")
    public ResponseEntity<ApiResponse> getFavorites(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        return ResponseEntity.ok(petService.getFavorites(token, page));
    }
}
