package com.poly.petfoster.controller.admin.pet;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.request.pets.PetRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pets.PetService;

@RestController
@RequestMapping("/api/admin/pets")
public class PetAdminController {

    @Autowired
    PetService petService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getPetManagement(@PathVariable String id) {
        return ResponseEntity.ok(petService.getPetManament(id));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> filterAdminPets(
            @RequestParam("name") Optional<String> name,
            @RequestParam("typeName") Optional<String> typeName,
            @RequestParam("colors") Optional<String> colors,
            @RequestParam("age") Optional<String> age,
            @RequestParam("gender") Optional<Boolean> gender,
            @RequestParam("status") Optional<String> status,
            @RequestParam("minDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> minDate,
            @RequestParam("maxDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> maxDate,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(
                petService.filterAdminPets(name, typeName, colors, age, gender, status, minDate, maxDate, sort, page));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createPet(@Valid @ModelAttribute PetRequest createPetRequest,
            @RequestPart List<MultipartFile> images) {
        return ResponseEntity.ok(petService.createPet(createPetRequest, images));
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePet(@PathVariable("id") String id,@Valid
            @RequestBody PetRequest petRequest) {
        return ResponseEntity.ok(petService.updatePet(id, petRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePet(@PathVariable("id") String id) {
        return ResponseEntity.ok(petService.deletePet(id));
    }

}
