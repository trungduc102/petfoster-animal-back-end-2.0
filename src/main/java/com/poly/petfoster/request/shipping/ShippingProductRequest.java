package com.poly.petfoster.request.shipping;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingProductRequest {
    @Builder.Default
    private String name = "";
    @Builder.Default
    private String code = "";
    @Builder.Default
    private Integer quantity = 1;
    @Builder.Default
    private Integer price = 0;
    // @Builder.Default
    // private Integer length = 12;
    // @Builder.Default
    // private Integer width = 12;
    // @Builder.Default
    // private Integer height = 12;
    @Builder.Default
    private Integer weight = 1200;
}
