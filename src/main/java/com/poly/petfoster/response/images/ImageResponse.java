package com.poly.petfoster.response.images;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private Integer id;
    private String name;
    private String image;
}
