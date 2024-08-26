package com.poly.petfoster.request.posts;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostUpdateRequest {
    private String title;
    private List<PostMediaUpdateRequest> medias;
}
