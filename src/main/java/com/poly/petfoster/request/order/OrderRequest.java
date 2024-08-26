package com.poly.petfoster.request.order;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class OrderRequest {

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer addressId;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer deliveryId;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer methodId;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer ship;

    @Valid
    @NotEmpty(message = RespMessage.NOT_EMPTY)
    private List<OrderItem> orderItems;
}
