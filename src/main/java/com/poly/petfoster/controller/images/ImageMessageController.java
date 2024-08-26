package com.poly.petfoster.controller.images;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.ImagesService;

@RestController
@RequestMapping("/api/user/messages")
public class ImageMessageController {

    @Autowired
    private ImagesService imagesService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> uploadImages(@RequestPart List<MultipartFile> images) {
        return ResponseEntity.ok(imagesService.uploadImages(images));
    }
}
