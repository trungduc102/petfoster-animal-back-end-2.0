package com.poly.petfoster.entity.Shipping;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province {
    private  Integer ProvinceID;   
    private String ProvinceName;   
    private String Code;  
}
