package com.poly.petfoster.controller.comments;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.comments.CommentPostRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.comments.CommentService;

@RestController
@RequestMapping("/api/user/comments")
public class CommentPostController {

    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> commentPosts(@RequestBody CommentPostRequest commentPostRequest,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(commentService.commentPost(commentPostRequest, token));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(commentService.deleteComment(id, token));
    }
}
