package com.poly.petfoster.response.comments;

import java.util.Date;
import java.util.List;

import com.poly.petfoster.response.users.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentResponse {
    private Integer id;
    private UserProfileResponse user;
    private String comment;
    private Integer likes;
    private Boolean isLike;
    private Date createAt;
    private List<CommentResponse> children;
    private Boolean owner;
}
