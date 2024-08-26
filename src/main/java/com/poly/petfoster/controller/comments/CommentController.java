package com.poly.petfoster.controller.comments;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.comments.CommentService;
import com.poly.petfoster.service.impl.comments.CommentServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("{post_id}")
    public ResponseEntity<ApiResponse> getCommentWithPost(@PathVariable("post_id") String uuid,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(commentService.getCommentWithIdPost(uuid, page));
    }

}
