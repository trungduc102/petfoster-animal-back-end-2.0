package com.poly.petfoster.request.product;


import javax.validation.constraints.NotBlank;

import com.poly.petfoster.entity.Product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRepoRequest {
    @NotBlank(message = "Product ID can't be blank!")

    private Integer id;
    private String productId;
    private Integer size;
    private Double inPrice;
    private Double outPrice;
    private Integer quantity;
    private Boolean inStock;

}
