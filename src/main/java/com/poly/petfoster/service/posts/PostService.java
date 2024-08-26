package com.poly.petfoster.service.posts;

import java.util.List;
import java.util.Optional;

import com.poly.petfoster.entity.social.Medias;
import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.request.comments.CommentPostRequest;
import com.poly.petfoster.request.posts.PostRequest;
import com.poly.petfoster.request.posts.PostUpdateRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.posts.PostMediaResponse;
import com.poly.petfoster.response.posts.PostResponse;

public interface PostService {
    List<PostResponse> buildPostHomePageResponses(List<Posts> posts);

    ApiResponse hightlight();

    ApiResponse hightlightOfUser(String username);

    PostMediaResponse builPostMediaResponse(Medias media);

    ApiResponse posts(Optional<String> search, Optional<Integer> page);

    ApiResponse postsOfUser(String username, Optional<Integer> page, Optional<String> type);

    ApiResponse detailPost(String uuid);

    ApiResponse likePost(String uuid, String token);

    ApiResponse deletePost(String uuid, String token);

    ApiResponse createPost(PostRequest data, String token);

    ApiResponse updatePost(PostUpdateRequest data, String id, String token);

}
