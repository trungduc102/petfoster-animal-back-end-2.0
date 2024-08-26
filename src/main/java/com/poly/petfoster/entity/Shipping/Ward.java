package com.poly.petfoster.entity.Shipping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ward {
    private Integer WardCode;
    private Integer DistrictID;
    private String WardName;
}
   
