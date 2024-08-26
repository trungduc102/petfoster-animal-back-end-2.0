package com.poly.petfoster.request.review;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewReplayRequest {
    private Integer idReplay;

    @NotBlank
    private String comment;
}
