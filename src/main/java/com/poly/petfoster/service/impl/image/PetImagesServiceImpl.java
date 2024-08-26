package com.poly.petfoster.service.impl.image;

import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.PetImgs;
import com.poly.petfoster.repository.PetImgsRepository;
import com.poly.petfoster.repository.PetRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.images.ImageResponse;
import com.poly.petfoster.service.admin.image.PetImagesService;
import com.poly.petfoster.ultils.ImageUtils;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class PetImagesServiceImpl implements PetImagesService {

    @Autowired
    PetImgsRepository petImgsRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    PortUltil portUltil;

    @Override
    public byte[] getImage(String fileName) {
        String filePath = "images/" + fileName;

        byte[] images;
        try {
            images = Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            try {
                images = Files.readAllBytes(new File("images/no-pet-image.jpg").toPath());
            } catch (IOException e1) {
                System.out.println("Error in getImage" + e.getMessage());
                images = null;
            }
        }
        return images;
    }

    @Override
    public ApiResponse deleteImgs(String id) {

        Pet pet = petRepository.findById(id).orElse(null);

        if (pet == null) {
            return ApiResponse.builder()
                    .message("Pet id is not exists")
                    .status(404)
                    .errors("PET_NOT_FOUND")
                    .build();
        }

        List<PetImgs> imgs = petImgsRepository.getImgsBypetId(id);
        for (PetImgs img : imgs) {
            String fileName = img.getNameImg();
            deleteImg(fileName);
            petImgsRepository.delete(img);
        }

        return ApiResponse.builder()
                .message("Successfully")
                .status(200)
                .errors(false)
                .data(imgs)
                .build();
    }

    @Override
    public ApiResponse deleteImage(String id, String nameImage) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            return ApiResponse.builder()
                    .message("Pet id is not exists")
                    .status(404)
                    .errors(true)
                    .build();
        }

        PetImgs image = petImgsRepository.findByNameImg(id, nameImage);

        if (image == null) {
            return ApiResponse.builder()
                    .message("Image id is not exists")
                    .status(404)
                    .errors(true)
                    .build();
        }

        String fileName = image.getNameImg();
        deleteImg(fileName);

        petImgsRepository.delete(image);

        return ApiResponse.builder()
                .message("Delete successfuly")
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(image)
                .build();
    }

    public void deleteImg(String fileName) {
        File imgFile = new File("images/" + fileName);
        if (imgFile.exists())
            imgFile.delete();
    }

    public ApiResponse getImagesByIdPet(String id) {

        if (id.isEmpty()) {
            return ApiResponse.builder()
                    .message("Pet id is not exists")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<PetImgs> images = petImgsRepository.getImgsBypetId(id);

        if (images.isEmpty()) {
            return ApiResponse.builder()
                    .message("Images is not empty")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<ImageResponse> imageResponses = images.stream().map((item) -> {
            return ImageResponse.builder()
                    .id(item.getId())
                    .name(item.getNameImg())
                    .image(portUltil.getUrlImage(item.getNameImg()))
                    .build();
        }).toList();

        return ApiResponse.builder()
                .message("Images successfuly")
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(imageResponses)
                .build();

    }

    public ApiResponse addImagesByIdPet(String id, List<MultipartFile> images) {

        int maxImages = 4;

        images.stream().forEach(item -> {
            System.out.println(item.getOriginalFilename());
        });

        if (id.isEmpty() || images.isEmpty()) {
            return ApiResponse.builder()
                    .message("Images or Id invalid ")
                    .status(400)
                    .errors(true)
                    .data(null)
                    .build();
        }

        if (images.size() > 0) {
            if (images.get(0).getOriginalFilename().equals("")) {
                return ApiResponse.builder()
                        .message("Images or Id invalid ")
                        .status(400)
                        .errors(true)
                        .data(null)
                        .build();
            }
        }

        Pet pet = petRepository.findById(id).orElse(null);
        List<PetImgs> imageOfPet = petImgsRepository.getImgsBypetId(id);

        if (pet == null) {
            return ApiResponse.builder()
                    .message("Pet not found ")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        if (imageOfPet.size() + images.size() > maxImages) {
            return ApiResponse.builder()
                    .message("Limit image for pet is 4")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        // all good

        List<PetImgs> newListImages = images.stream().map((image) -> {

            try {
                File file = ImageUtils.createFileImage();

                image.transferTo(new File(file.getAbsolutePath()));
                PetImgs newImages = PetImgs.builder()
                        .nameImg(file.getName())
                        .pet(pet)
                        .build();

                return newImages;

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;

            }
        }).toList();

        return ApiResponse.builder()
                .message("Add images successfuly")
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(petImgsRepository.saveAll(newListImages))
                .build();
    }

    @Override
    public ApiResponse uploadImages(List<MultipartFile> images) {

        if (images.isEmpty()) {
            return ApiResponse.builder()
                    .message("Data invalid")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<String> newListImages = images.stream().map((image) -> {

            try {
                File file = ImageUtils.createFileImage("messages\\");

                image.transferTo(new File(file.getAbsolutePath()));

                return portUltil.getUrlImage(file.getName(), "messages");

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;

            }
        }).toList();

        return ApiResponse.builder()
                .message("Add images successfuly")
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(newListImages)
                .build();
    }

    @Override
    public byte[] getImage(String fileName, String pathName) {
        String filePath = "images/" + pathName + "/" + fileName;

        byte[] images;
        try {
            images = Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            try {
                images = Files.readAllBytes(new File("images/no-pet-image.jpg").toPath());
            } catch (IOException e1) {
                System.out.println("Error in getImage" + e.getMessage());
                images = null;
            }
        }
        return images;
    }

    @Override
    public ApiResponse uploadImages(List<MultipartFile> images, String path) {
        if (images.isEmpty()) {
            return ApiResponse.builder()
                    .message("Data invalid")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<String> newListImages = images.stream().map((image) -> {

            try {
                File file = ImageUtils.createFileImage(path + "\\");

                image.transferTo(new File(file.getAbsolutePath()));

                return portUltil.getUrlImage(file.getName(), path);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;

            }
        }).toList();

        return ApiResponse.builder()
                .message("Add images successfuly")
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(newListImages)
                .build();
    }
}
