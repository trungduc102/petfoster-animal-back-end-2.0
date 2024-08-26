package com.poly.petfoster.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.impl.image.items.GetMediasItem;

public interface ImagesService {
    byte[] getImage(String fileName);

    byte[] getImage(String fileName, String pathName);

    byte[] getImage(String fileName, String pathName, String defaultImageWhenWrong);

    GetMediasItem getMedias(String fileName, String pathName);

    ApiResponse deleteImgs(String id);

    ApiResponse getImagesByIdProduct(String id);

    ApiResponse addImagesByIdProduct(String id, List<MultipartFile> images);

    ApiResponse deleteImage(String id, Integer idImage);

    ApiResponse deleteMedia(Integer idImage, String token);

    ApiResponse uploadImages(List<MultipartFile> images);

    ApiResponse uploadImages(List<MultipartFile> images, String path);

}
