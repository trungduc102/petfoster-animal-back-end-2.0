package com.poly.petfoster.service.impl.image;

import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

import com.poly.petfoster.entity.Imgs;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.entity.social.Medias;
import com.poly.petfoster.repository.ImgsRepository;
import com.poly.petfoster.repository.MediasRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.images.ImageResponse;
import com.poly.petfoster.service.ImagesService;
import com.poly.petfoster.service.impl.image.items.GetMediasItem;
import com.poly.petfoster.service.impl.user.UserServiceImpl;
import com.poly.petfoster.service.posts.PostService;
import com.poly.petfoster.ultils.ImageUtils;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImgsRepository imgsRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PortUltil portUltil;

    @Autowired
    private MediasRepository mediasRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public byte[] getImage(String fileName) {
        String filePath = "images/" + fileName;

        byte[] images;
        try {
            images = Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            try {
                images = Files.readAllBytes(new File("images/no-product-image.jpg").toPath());
            } catch (IOException e1) {
                System.out.println("Error in getImage" + e.getMessage());
                images = null;
            }
        }
        return images;
    }

    @Override
    public ApiResponse deleteImgs(String id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ApiResponse.builder().message("Product id is not exists").status(404).errors("PRODUCT_NOT_FOUND")
                    .build();
        }

        List<Imgs> imgs = imgsRepository.getImgsByProductId(id);
        for (Imgs img : imgs) {
            String fileName = img.getNameImg();
            deleteImg(fileName);
            imgsRepository.delete(img);
        }

        return ApiResponse.builder().message("Successfully").status(200).errors(false).data(imgs).build();
    }

    @Override
    public ApiResponse deleteImage(String id, Integer idImage) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ApiResponse.builder().message("Product id is not exists").status(404).errors(true)
                    .build();
        }

        Imgs image = imgsRepository.getImageByProductId(id, idImage);

        if (image == null) {
            return ApiResponse.builder().message("Image id is not exists").status(404).errors(true)
                    .build();
        }

        String fileName = image.getNameImg();
        deleteImg(fileName);

        imgsRepository.delete(image);

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

    @Override
    public ApiResponse getImagesByIdProduct(String id) {

        if (id.isEmpty()) {
            return ApiResponse.builder()
                    .message("Product id is not exists")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<Imgs> images = imgsRepository.getImgsByProductId(id);

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

    @Override
    public ApiResponse addImagesByIdProduct(String id, List<MultipartFile> images) {

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

        Product product = productRepository.findById(id).orElse(null);
        List<Imgs> imageOfProduct = imgsRepository.getImgsByProductId(id);

        if (product == null) {
            return ApiResponse.builder()
                    .message("Product not found ")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        if (imageOfProduct.size() + images.size() > maxImages) {
            return ApiResponse.builder()
                    .message("Limit image for product is 4")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        // all good

        List<Imgs> newListImages = images.stream().map((image) -> {

            try {
                File file = ImageUtils.createFileImage();

                image.transferTo(new File(file.getAbsolutePath()));
                Imgs newImages = Imgs.builder()
                        .nameImg(file.getName())
                        .product(product)
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
                .data(imgsRepository.saveAll(newListImages))
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
                images = Files.readAllBytes(new File("images/no-product-image.jpg").toPath());
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

    @Override
    public GetMediasItem getMedias(String fileName, String pathName) {
        Medias media = mediasRepository.findByName(fileName);

        GetMediasItem getMediasItem = GetMediasItem.builder()
                .data(this.getImage(fileName, pathName, "planhorder-image.png")).build();

        if (media != null) {
            File file = new File("images/medias/" + media.getName());
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());

            getMediasItem.setContentType(mimeType);
            getMediasItem.setOriginaFile(file);
        }

        return getMediasItem;
    }

    @Override
    public byte[] getImage(String fileName, String pathName, String defaultImageWhenWrong) {
        String filePath = "images/" + pathName + "/" + fileName;

        byte[] images;
        try {
            images = Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            try {
                images = Files.readAllBytes(new File("images/" + defaultImageWhenWrong).toPath());
            } catch (IOException e1) {
                System.out.println("Error in getImage" + e.getMessage());
                images = null;
            }
        }
        return images;
    }

    @Override
    public ApiResponse deleteMedia(Integer idImage, String token) {

        if (idImage == null || token == null) {
            return ApiResponse.builder()
                    .message("Data invalid")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        User user = userServiceImpl.getUserFromToken(token);
        Medias media = mediasRepository.findById(idImage).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("Un authorization")
                    .status(HttpStatus.FORBIDDEN.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        if (media == null) {
            return ApiResponse.builder()
                    .message("Data not found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        ImageUtils.deleteImg("medias/" + media.getName());

        mediasRepository.delete(media);

        return ApiResponse.builder()
                .message("Successfuly")
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(postService.builPostMediaResponse(media))
                .build();

    }
}
