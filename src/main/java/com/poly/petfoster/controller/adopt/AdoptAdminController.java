package com.poly.petfoster.controller.adopt;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.adopts.CancelAdoptRequest;
import com.poly.petfoster.request.adopts.UpdatePickUpDateRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.adopt.AdoptService;

@RestController
@RequestMapping("/api/admin/adopts")
public class AdoptAdminController {

    @Autowired
    AdoptService adoptService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> filterAdopts(
            @RequestParam("name") Optional<String> name,
            @RequestParam("petName") Optional<String> petName,
            @RequestParam("status") Optional<String> status,
            @RequestParam("registerStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> registerStart,
            @RequestParam("registerEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> registerEnd,
            @RequestParam("adoptStart") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> adoptStart,
            @RequestParam("adoptEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> adoptEnd,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(adoptService.filterAdopts(name, petName, status, registerStart, registerEnd,
                adoptStart, adoptEnd, sort, page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> acceptAdoption(@PathVariable Integer id,
            @Valid @RequestBody UpdatePickUpDateRequest updatePickUpDateRequest) {
        return ResponseEntity.ok(adoptService.acceptAdoption(id, updatePickUpDateRequest));
    }

    @GetMapping("/{adoptId}")
    public ResponseEntity<ApiResponse> getAdoptionOtherUser(
            @PathVariable Integer adoptId) {
        return ResponseEntity.ok(adoptService.getAdoptOtherUser(adoptId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse> cancelAdopt(@PathVariable Integer id,
            @Valid @RequestBody CancelAdoptRequest cancelAdoptRequest) {
        return ResponseEntity.ok(adoptService.cancelAdopt(id, cancelAdoptRequest));
    }

    @PutMapping("/confirmed/{id}")
    public ResponseEntity<ApiResponse> doneAdoption(@PathVariable Integer id) {
        return ResponseEntity.ok(adoptService.doneAdoption(id));
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse> report() {
        return ResponseEntity.ok(adoptService.reprots());

    }

}
