package com.poly.petfoster.controller.like_comment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.comments.CommentService;

@RestController
@RequestMapping("/api/user/like-comments")
public class LikeCommentController {
    @Autowired
    private CommentService commentService;

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> likeComment(@PathVariable("id") Integer idComment,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(commentService.likeComment(idComment, token));
    }
}
