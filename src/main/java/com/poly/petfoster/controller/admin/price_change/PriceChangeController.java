package com.poly.petfoster.controller.admin.price_change;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.price_changes.PriceChangeService;

@RestController
@RequestMapping("/api/admin/price-changes")
public class PriceChangeController {

    @Autowired
    private PriceChangeService priceChangeService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPriceChange(@PathVariable("id") String idProduct) {
        return ResponseEntity.ok(priceChangeService.getPriceChange(idProduct));
    }
}
