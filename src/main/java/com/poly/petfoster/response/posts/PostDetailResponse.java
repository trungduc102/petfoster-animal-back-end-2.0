package com.poly.petfoster.response.posts;

import java.util.Date;
import java.util.List;

import com.poly.petfoster.response.users.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDetailResponse {
    private String id;
    private String title;
    private Boolean isLike;
    private Integer likes;
    private Integer comments;
    private UserProfileResponse user;
    private List<PostMediaResponse> images;
    private Date createdAt;
    private Boolean owner;
    private Boolean edit;

}
