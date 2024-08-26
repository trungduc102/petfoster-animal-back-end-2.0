package com.poly.petfoster.request.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentPostRequest {
    private String uuid;
    private String comment;
    private Integer replayId;
}
