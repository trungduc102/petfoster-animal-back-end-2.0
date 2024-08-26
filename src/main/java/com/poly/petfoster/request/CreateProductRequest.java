package com.poly.petfoster.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.entity.Brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank(message = "Product Name can't be blank!")
    private String name;

    @NotBlank(message = "Product Desciption can't be blank!")
    private String description;

    @NotBlank(message = "Product Type can't be blank!")
    private String type;

    @NotBlank(message = "Product Type can't be blank!")
    private Integer brand;

    @Valid
    private List<ProductRepoRequest> repo;

    private List<MultipartFile> images;

}

// type DataProductType = {
// name: string;
// type: string;
// brand: string;
// images: File[];
// repo: RepoType[];
// description: string;
// };