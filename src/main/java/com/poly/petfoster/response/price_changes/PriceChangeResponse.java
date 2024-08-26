package com.poly.petfoster.response.price_changes;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceChangeResponse {

    private Integer id;
    private Double newInPrice;
    private Double newOutPrice;
    private Double oldInPrice;
    private Double oldOutPrice;
    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
    private Date updateAt;
    private Integer size;
    private Object user;

}
