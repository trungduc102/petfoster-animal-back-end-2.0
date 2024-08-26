package com.poly.petfoster.request.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginWithFacebookResquest {
    private String uuid;
    private String username;
    private String avartar;
}
