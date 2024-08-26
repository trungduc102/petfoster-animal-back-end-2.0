package com.poly.petfoster.service.admin.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.response.ApiResponse;

public interface PetImagesService {
    byte[] getImage(String fileName);

    byte[] getImage(String fileName, String pathName);

    ApiResponse deleteImgs(String id);

    ApiResponse getImagesByIdPet(String id);

    ApiResponse addImagesByIdPet(String id, List<MultipartFile> images);

    ApiResponse deleteImage(String id, String nameImage);

    ApiResponse uploadImages(List<MultipartFile> images);

    ApiResponse uploadImages(List<MultipartFile> images, String path);

}
