package com.poly.petfoster.response.posts;

import com.poly.petfoster.response.ProfileResponse;
import com.poly.petfoster.response.users.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostResponse {
    private String id;
    private String title;
    private String thumbnail;
    private Boolean containVideo;
    private Boolean isLike;
    private Integer likes;
    private Integer comments;
    private UserProfileResponse user;

}
