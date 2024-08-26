package com.poly.petfoster.controller.address;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.addresses.AddressUserRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.address.AddressService;

@RestController
@RequestMapping("/api/user/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/default")
    public ResponseEntity<ApiResponse> getAddressDefault(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(addressService.getDefaultAddress(token));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAddresses(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(addressService.getAddressByToken(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAddressById(@RequestHeader("Authorization") String token,
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(addressService.getAddressById(token, id));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(@RequestHeader("Authorization") String token,
            @RequestBody @Valid AddressUserRequest data) {
        return ResponseEntity.ok(addressService.create(token, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@RequestHeader("Authorization") String token,
            @RequestBody @Valid AddressUserRequest data, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(addressService.update(token, id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@RequestHeader("Authorization") String token,
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(addressService.delete(token, id));
    }
}
