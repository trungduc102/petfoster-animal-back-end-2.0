package com.poly.petfoster.request.addresses;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressUserRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    private AddressRequest address;
    private boolean setDefault;
}
