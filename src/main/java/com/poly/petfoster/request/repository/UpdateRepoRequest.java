package com.poly.petfoster.request.repository;

import javax.validation.constraints.NotBlank;

import com.poly.petfoster.constant.RespMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRepoRequest {

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Double inPrice;
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Double outPrice;
    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Integer quantity;

}
