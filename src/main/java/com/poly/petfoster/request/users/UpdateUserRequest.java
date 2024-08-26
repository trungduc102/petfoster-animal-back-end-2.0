package com.poly.petfoster.request.users;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String id;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String fullname;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Optional<Date> birthday;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Boolean gender;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String phone;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String address;

    private MultipartFile avatar;

    @NotEmpty(message = RespMessage.NOT_EMPTY)
    @Email(message = "is invalid")
    private String email;

}
