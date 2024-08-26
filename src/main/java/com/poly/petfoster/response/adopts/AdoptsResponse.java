package com.poly.petfoster.response.adopts;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poly.petfoster.response.pets.PetResponse;
import com.poly.petfoster.response.users.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptsResponse {
    private Integer id;
    private String state;
    private UserProfileResponse user;
    private PetResponse pet;
    private Date registerAt;
    private Date adoptAt; // can null if watting or cancel
    private String cancelReason;
    private String phone;
    private String address;
    private Date pickUpDate;
}
