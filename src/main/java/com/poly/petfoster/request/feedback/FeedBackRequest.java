package com.poly.petfoster.request.feedback;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedBackRequest {
    private String fullname;
    private String phone;
    @NotBlank
    private String email;
    @NotBlank
    private String message;
}
