package com.poly.petfoster.response;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.poly.petfoster.constant.RespMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String id;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String username;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String fullname;

    @NotEmpty(message = RespMessage.NOT_EMPTY)
    @Email(message = "is invalid")
    private String email;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String phone;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Boolean gender;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private Date birthday;
}
