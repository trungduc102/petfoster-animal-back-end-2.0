package com.poly.petfoster.response.pages;

import java.util.List;

import com.poly.petfoster.response.pets.PetDetailResponse;
import com.poly.petfoster.response.pets.PetResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PetDetailPageResponse {
    private PetDetailResponse pet;
    private List<PetResponse> orthers;
}
