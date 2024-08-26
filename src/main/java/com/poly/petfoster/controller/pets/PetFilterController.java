package com.poly.petfoster.controller.pets;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pets.PetService;

@RestController
@RequestMapping("/api/pets")
public class PetFilterController {

    @Autowired
    PetService petService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> filterPets(
            @RequestParam("name") Optional<String> name,
            @RequestParam("typeName") Optional<String> typeName,
            @RequestParam("colors") Optional<String> colors,
            @RequestParam("age") Optional<String> age,
            @RequestParam("gender") Optional<Boolean> gender,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(petService.filterPets(name, typeName, colors, age, gender, sort, page));
    }

    @GetMapping("attributes")
    public ResponseEntity<ApiResponse> getAttributes() {
        return ResponseEntity.ok(petService.getAttributes());
    }

}
