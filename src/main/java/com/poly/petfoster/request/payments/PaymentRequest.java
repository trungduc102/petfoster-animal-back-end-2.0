package com.poly.petfoster.request.payments;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    
    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer orderId;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer amount;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Boolean isPaid;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String payAt;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer transactionNumber;

    private PaymentMethod paymentMethod;

}
