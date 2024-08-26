package com.poly.petfoster.request.users;

import java.util.Date;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateaUserManageRequest {
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private String password;
    private Boolean gender;
    private String role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Optional<Date> birthday;
    private String address;
    private MultipartFile avatar;

}
