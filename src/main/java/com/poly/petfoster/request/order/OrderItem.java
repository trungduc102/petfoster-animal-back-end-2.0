package com.poly.petfoster.request.order;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.poly.petfoster.constant.RespMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String productId;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer size;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer quantity;

}
