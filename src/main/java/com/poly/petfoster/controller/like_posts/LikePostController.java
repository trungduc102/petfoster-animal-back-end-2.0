package com.poly.petfoster.controller.like_posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.posts.PostService;

@RestController
@RequestMapping("/api/user/like-posts")
public class LikePostController {

    @Autowired
    private PostService postService;

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> likePost(@PathVariable("id") String uuid,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(postService.likePost(uuid, token));
    }
}
