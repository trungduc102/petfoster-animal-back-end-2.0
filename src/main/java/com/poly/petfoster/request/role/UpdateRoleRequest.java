package com.poly.petfoster.request.role;

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
public class UpdateRoleRequest {

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String userId;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Integer roleId;

}
