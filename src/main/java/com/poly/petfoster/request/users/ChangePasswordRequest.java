package com.poly.petfoster.request.users;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotBlank
    @Length(min = 6, message = "must be longer than 6 characters!")
    private String password;
    @NotBlank
    @Length(min = 6, message = "must be longer than 6 characters!")
    private String newPassword;

}
