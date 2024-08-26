package com.poly.petfoster.response.pets;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PetAttributesReponse {
    List<PetAttributeReponse> colors;
    List<PetAttributeReponse> states;
    List<PetAttributeReponse> breeds;
    List<PetAttributeReponse> typies;
}
