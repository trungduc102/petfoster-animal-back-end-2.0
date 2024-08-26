package com.poly.petfoster.controller.admin.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.address.AddressService;

@RestController
@RequestMapping("/api/admin/addresses")
public class ManagerAddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserAddresses(@PathVariable("username") String username) {
        return ResponseEntity.ok(addressService.getUserAddresses(username));
    }
}
