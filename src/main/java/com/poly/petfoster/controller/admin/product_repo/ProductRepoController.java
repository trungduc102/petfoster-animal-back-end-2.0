package com.poly.petfoster.controller.admin.product_repo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.repository.CreateRepoRequest;
import com.poly.petfoster.request.repository.UpdateRepoRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.product_repo.ProductRepoService;

@RestController
@RequestMapping("/api/admin/product-repo/")
public class ProductRepoController {

    @Autowired
    ProductRepoService productRepoService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getProductRepositories(@PathVariable("id") String id) {
        return ResponseEntity.ok(productRepoService.getProductRepositories(id));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProductRepo(@PathVariable(value = "id") Integer id) {
        System.out.println(id);
        return ResponseEntity.ok(productRepoService.deleteProductRepo(id));
    }

    @PostMapping("{id}/u")
    public ResponseEntity<ApiResponse> updateProductRepo(@PathVariable("id") Integer id,
            @RequestBody UpdateRepoRequest updateRepoRequest, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(productRepoService.updateOrCreateRepo(id, updateRepoRequest, token));

    }

    @PostMapping("{id}/c")
    public ResponseEntity<ApiResponse> createProductRepo(@PathVariable("id") String id,
            @RequestBody CreateRepoRequest createRepoRequest) {
        return ResponseEntity.ok(productRepoService.addAProductRepository(id, createRepoRequest));

    }
}
