package com.poly.petfoster.response.addresses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressUserResponse {
    private Integer id;
    private String name;
    private String phone;
    private AddressResponse address;
    private boolean isDefault;

    public boolean getIsDefault() {
        return this.isDefault;
    }
}
