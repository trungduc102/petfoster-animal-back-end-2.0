package com.poly.petfoster.response;

import java.util.Date;
import java.util.List;

import com.poly.petfoster.entity.Imgs;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.ProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String desc;
    private ProductType productType;
    private String brand;
    private Date createAt;
    private Boolean isActive;
    private List<ProductRepo> productsRepo;
    private List<OrderDetail> orderDetails;
    private List<Imgs> imgs;

}
