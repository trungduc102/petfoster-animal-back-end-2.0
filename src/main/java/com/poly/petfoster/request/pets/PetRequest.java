package com.poly.petfoster.request.pets;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PetRequest {
    @NotBlank(message = "Pet Name can't be blank!")
    @NotEmpty(message = "Pet Name can't be empty!")
    private String name;
    @NotBlank(message = "Pet Color can't be blank!")
    @NotEmpty(message = "Pet Name can't be empty!")

    private String color;
    private Boolean isSpay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fosterAt;
    private String description;
    @NotBlank(message = "Pet Size can't be blank!")
    @NotEmpty(message = "Pet Name can't be empty!")

    private String size; // size <=> age

    private Boolean sex;
    @NotBlank(message = "Pet Breed ID can't be blank!")
    @NotEmpty(message = "Pet Name can't be empty!")
    private String breed; // => breed id

    private String status;
}
