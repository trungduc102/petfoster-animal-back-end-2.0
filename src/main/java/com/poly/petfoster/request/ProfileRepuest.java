package com.poly.petfoster.request;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.constant.RespMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRepuest {

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String fullname;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    private String phone;
    @NotEmpty(message = RespMessage.NOT_EMPTY)
    @Email(message = "is invalid")
    private String email;

    @NotNull(message = RespMessage.NOT_EMPTY)
    private Boolean gender;

    @NotBlank(message = RespMessage.NOT_EMPTY)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Optional<Date> birthday;

    private MultipartFile avartar;

}
