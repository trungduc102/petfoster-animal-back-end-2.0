package com.poly.petfoster.service.comments;

import java.util.Optional;

import com.poly.petfoster.request.comments.CommentPostRequest;
import com.poly.petfoster.response.ApiResponse;

public interface CommentService {
    ApiResponse getCommentWithIdPost(String uuid, Optional<Integer> page);

    ApiResponse likeComment(Integer idComment, String token);

    ApiResponse commentPost(CommentPostRequest commentPostRequest, String token);

    ApiResponse deleteComment(Integer id, String token);

}
