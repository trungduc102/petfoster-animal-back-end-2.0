package com.poly.petfoster.response.users;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserManageResponse {
    private String id;
    private String username;
    private String fullname;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private boolean gender;
    private String phone;
    private String address;
    private String avatar;
    private String email;
    private String role;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    private String password;
    private Boolean active;
    private String displayName;
}
