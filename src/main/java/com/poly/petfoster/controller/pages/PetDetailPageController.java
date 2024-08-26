package com.poly.petfoster.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pets.PetService;

@RestController
@RequestMapping("/api/pets")
public class PetDetailPageController {

    @Autowired
    private PetService petService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDetailPet(@PathVariable("id") String id) {
        return ResponseEntity.ok(petService.getDetailPet(id));
    }
}
