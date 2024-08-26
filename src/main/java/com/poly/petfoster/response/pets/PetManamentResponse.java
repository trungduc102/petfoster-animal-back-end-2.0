package com.poly.petfoster.response.pets;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetManamentResponse {
    private String id;
    private String breed;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fostered;
    private String size;
    private String sex;
    private String type;
    private Boolean spay;
    private List<String> images;
    private String color;
    private String status;
}
