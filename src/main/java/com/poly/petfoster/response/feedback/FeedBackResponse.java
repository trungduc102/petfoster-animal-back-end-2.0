package com.poly.petfoster.response.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedBackResponse {
    private String fullname;
    private String email;
    private String message;
}
