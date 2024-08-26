package com.poly.petfoster.controller.posts;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.posts.PostRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.posts.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/hightlight")
    public ResponseEntity<ApiResponse> getAttributes() {
        return ResponseEntity.ok(postService.hightlight());
    }

    @GetMapping("/hightlight/{username}")
    public ResponseEntity<ApiResponse> getAttributes(@PathVariable("username") String username) {
        return ResponseEntity.ok(postService.hightlightOfUser(username));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> posts(@RequestParam("search") Optional<String> search,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(postService.posts(search, page));
    }

    @GetMapping("{username}")
    public ResponseEntity<ApiResponse> postsOfUser(@PathVariable("username") String username,
            @RequestParam("type") Optional<String> type, @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(postService.postsOfUser(username, page, type));
    }

    @GetMapping("/detail/{uuid}")
    public ResponseEntity<ApiResponse> detailPost(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(postService.detailPost(uuid));
    }

}
