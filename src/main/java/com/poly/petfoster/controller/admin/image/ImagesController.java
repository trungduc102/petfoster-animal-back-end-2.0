package com.poly.petfoster.controller.admin.image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.ImagesService;

@RestController
@RequestMapping("/api/admin/images")
public class ImagesController {

    @Autowired
    private ImagesService imageService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getImagesByIdProduct(@PathVariable("id") String id) {
        return ResponseEntity.ok(imageService.getImagesByIdProduct(id));
    }

    @PostMapping("{id}")
    public ResponseEntity<ApiResponse> addImagesByIdProduct(@PathVariable("id") String id,
            @RequestPart("images") List<MultipartFile> images) {
        return ResponseEntity.ok(imageService.addImagesByIdProduct(id, images));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteImgs(@PathVariable("id") String id) {
        return ResponseEntity.ok(imageService.deleteImgs(id));
    }

    @DeleteMapping("{id}/{idImage}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable("id") String id,
            @PathVariable("idImage") Integer idImage) {
        return ResponseEntity.ok(imageService.deleteImage(id, idImage));
    }
}
