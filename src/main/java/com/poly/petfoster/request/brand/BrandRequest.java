package com.poly.petfoster.request.brand;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.poly.petfoster.constant.RespMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequest {
    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer id;
    @NotNull(message = RespMessage.NOT_EMPTY)
    @NotBlank(message = "Name can't be blank")
    private String name;
}
