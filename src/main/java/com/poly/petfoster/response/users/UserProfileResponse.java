package com.poly.petfoster.response.users;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String id;
    private String username;
    private String fullname;
    private Date birthday;
    private boolean gender;
    private String phone;
    private String email;
    private String avatar;
    private String role;
    private String displayName;
    private String provider;
    private Date createAt;

}
