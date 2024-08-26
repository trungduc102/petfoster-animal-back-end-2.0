package com.poly.petfoster.entity.Shipping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class District {
    private Integer DistrictID;
    private Integer ProvinceID;
    private String DistrictName;
    private String Code;
    private Integer Type;
    private Integer SupportType;
}
