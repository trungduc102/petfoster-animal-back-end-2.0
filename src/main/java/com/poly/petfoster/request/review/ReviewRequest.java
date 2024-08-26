package com.poly.petfoster.request.review;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer orderId;
    @NotNull(message = RespMessage.NOT_EMPTY)
    private String productId;
    @NotNull(message = RespMessage.NOT_EMPTY)
    @NotBlank(message = "Comment can't be blank")
    private String comment;
    @NotNull(message = RespMessage.NOT_EMPTY)
    @Min(value = 1, message = "Min is 1")
    @Max(value = 5, message = "Max is 5")
    private Integer rate;
}
