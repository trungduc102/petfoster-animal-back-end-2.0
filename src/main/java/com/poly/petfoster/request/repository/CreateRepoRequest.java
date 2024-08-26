package com.poly.petfoster.request.repository;

import javax.validation.constraints.NotBlank;

import com.poly.petfoster.constant.RespMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRepoRequest {
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Integer size;
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Double inPrice;
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Double outPrice;
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Integer quantity;
}
