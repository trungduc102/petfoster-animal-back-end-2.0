package com.poly.petfoster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.poly.petfoster.service.ImagesService;
import com.poly.petfoster.service.impl.image.items.GetMediasItem;

@RestController
@RequestMapping("/images/")
public class ImageController {
    @Autowired
    private ImagesService imageService;

    @GetMapping("{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageService.getImage(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("/messages/{fileName}")
    public ResponseEntity<?> downloadImageMessages(@PathVariable String fileName) {
        byte[] imageData = imageService.getImage(fileName, "messages");

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("/notification/{fileName}")
    public ResponseEntity<?> downloadImageNotitfication(@PathVariable String fileName) {
        byte[] imageData = imageService.getImage(fileName, "notification");

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @SuppressWarnings("null")
    @GetMapping("/medias/{fileName}")
    public ResponseEntity<?> downloadMedias(@PathVariable String fileName) {
        GetMediasItem imageData = imageService.getMedias(fileName, "medias");

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType
                        .valueOf(imageData.getContentType() == null ? "image/png" : imageData.getContentType()))
                .body(imageData.getData());
    }

}
