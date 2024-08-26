package com.poly.petfoster.response.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileMessageResponse {
    private String id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private String avatar;
}
