package com.poly.petfoster.response.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostMediaResponse {
    private Integer id;
    private String url;
    private Boolean isVideo;
    private Integer index;
}
