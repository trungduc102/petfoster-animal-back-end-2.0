package com.poly.petfoster.response.pets;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PetDetailResponse {
    private String id;
    private String breed;
    private String name;
    private String image;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fostered;
    private String size;
    private String sex;
    private String type;
    private Integer fosterDate;
    private String sterilization;
    private Boolean like;
    private List<String> images;
    private String color;
    private Boolean canAdopt;
    private String status;
}
