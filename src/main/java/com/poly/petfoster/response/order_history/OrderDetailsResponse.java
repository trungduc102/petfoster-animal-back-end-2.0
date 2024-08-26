package com.poly.petfoster.response.order_history;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsResponse {

    private Integer id;

    private String address;

    private String placedDate;

    private String deliveryMethod;

    private String name;

    private String paymentMethod;

    private String phone;

    private String description;

    private List<OrderProductItem> products;

    private Integer shippingFee;

    private Integer subTotal;

    private Integer quantity;

    private Integer total;

    private String state;

    private String expectedTime;

    private String username;

    private String displayName;

    private Boolean read;

    private Integer print;

    private String token;

}
