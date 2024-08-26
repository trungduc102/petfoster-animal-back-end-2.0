package com.poly.petfoster.service.admin.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.response.ApiResponse;

public interface ImagesService {
    byte[] getImage(String fileName);

    ApiResponse deleteImgs(String id);

    ApiResponse getImagesByIdProduct(String id);

    ApiResponse addImagesByIdProduct(String id, List<MultipartFile> images);

    ApiResponse deleteImage(String id, Integer idImage);

}
