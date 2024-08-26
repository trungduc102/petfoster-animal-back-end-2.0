package com.poly.petfoster.request;

import javax.validation.constraints.NotNull;

import com.poly.petfoster.constant.RespMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRepoRequest {

    @NotNull(message = RespMessage.NOT_EMPTY)
    Integer size;

    @NotNull(message = RespMessage.NOT_EMPTY)
    Integer quantity;

    @NotNull(message = RespMessage.NOT_EMPTY)
    Integer inPrice;

    @NotNull(message = RespMessage.NOT_EMPTY)
    Integer outPrice;

}

//  size: number;
//     quantity: number;
//     inPrice: number;
//     outPrice: number;