package com.poly.petfoster.service.impl.comments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.entity.social.Comments;
import com.poly.petfoster.entity.social.LikedComments;
import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.repository.CommentRepository;
import com.poly.petfoster.repository.LikeCommentRepository;
import com.poly.petfoster.repository.PostsRepository;
import com.poly.petfoster.request.comments.CommentPostRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.comments.CommentResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.service.comments.CommentService;
import com.poly.petfoster.service.impl.user.UserServiceImpl;
import com.poly.petfoster.service.user.UserService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    public CommentResponse buildCommentResponse(Comments comments) {

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader("Authorization");

        boolean isLike = false;
        boolean owner = false;
        List<Comments> children = new ArrayList<>();

        if (token != null) {

            User user = userServiceImpl.getUserFromToken(token);

            isLike = likeCommentRepository.existByUserAndComment(user.getId(),
                    comments.getId()) != null;
            if (user != null) {
                owner = user.getUsername().equals(comments.getUser().getUsername()) || userServiceImpl.isAdmin(user);
            }

        }

        List<Comments> commentsReplaies = commentRepository.findByReply(comments.getPost().getId(), comments.getId());

        if (commentsReplaies != null) {
            children = commentsReplaies;
        }

        return CommentResponse.builder()
                .id(comments.getId())
                .comment(comments.getComment())
                .user(userServiceImpl.buildUserProfileResponse(comments.getUser()))
                .createAt(comments.getCreateAt())
                .likes(comments.getLikedComments() != null ? comments.getLikedComments().size() : 0)
                .isLike(isLike)
                .children(this.buildCommentResponses(children))
                .owner(owner)
                .build();
    }

    public List<CommentResponse> buildCommentResponses(List<Comments> comments) {

        return comments.stream().map(item -> {
            return buildCommentResponse(item);
        }).toList();
    }

    @Override
    public ApiResponse getCommentWithIdPost(String uuid, Optional<Integer> page) {

        Posts posts = postsRepository.findByUuid(uuid);

        List<Comments> comments = commentRepository.findByPost(posts);

        if (posts == null || comments == null || comments.isEmpty()) {
            return ApiResponse.builder()
                    .message("Failure")
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                    .build();
        }

        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), comments.size());

        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                    .errors(false)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<Comments> visibleComments = comments.subList(startIndex, endIndex);

        if (visibleComments == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                    .errors(false)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        Page<Comments> pagination = new PageImpl<Comments>(visibleComments, pageable,
                comments.size());

        return ApiResponse.builder()
                .message("Successfuly")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(PagiantionResponse.builder().data(buildCommentResponses(visibleComments))
                        .pages(pagination.getTotalPages()).build())
                .build();
    }

    @Override
    public ApiResponse likeComment(Integer idComment, String token) {

        if (idComment == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        if (token == null) {
            return ApiResponse.builder()
                    .message("Please login to use")
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        Comments comments = commentRepository.findById(idComment).orElse(null);
        User user = userServiceImpl.getUserFromToken(token);

        LikedComments cheLikedComments = likeCommentRepository.existByUserAndComment(user.getId(), comments.getId());

        if (cheLikedComments != null) {
            likeCommentRepository.delete(cheLikedComments);
            return ApiResponse.builder()
                    .message("Successfuly")
                    .errors(false)
                    .status(HttpStatus.OK.value())
                    .data(buildCommentResponse(comments))
                    .build();
        }

        if (comments == null || user == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        LikedComments likedComments = LikedComments.builder()
                .comment(comments)
                .user(user)
                .build();

        if (likedComments == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        likeCommentRepository.save(likedComments);

        return ApiResponse.builder()
                .message("Successfuly")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(buildCommentResponse(comments))
                .build();
    }

    @Override
    public ApiResponse commentPost(CommentPostRequest commentPostRequest, String token) {
        if (commentPostRequest == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        if (token == null) {
            return ApiResponse.builder()
                    .message("Please login to use")
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
        Posts posts = postsRepository.findByUuid(commentPostRequest.getUuid());
        User user = userServiceImpl.getUserFromToken(token);

        if (posts == null || user == null) {
            return ApiResponse.builder()
                    .message("Something not found")
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        Comments comments = Comments.builder()
                .comment(commentPostRequest.getComment())
                .replyTo(commentPostRequest.getReplayId())
                .post(posts)
                .user(user)
                .build();

        if (comments == null) {
            return ApiResponse.builder()
                    .message("Something wrong")
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        // save comment
        commentRepository.save(comments);
        return ApiResponse.builder()
                .message("Successfuly")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(buildCommentResponse(comments))
                .build();
    }

    @Override
    public ApiResponse deleteComment(Integer id, String token) {
        if (id == null) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        if (token == null) {
            return ApiResponse.builder()
                    .message("Please login to use")
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        User user = userServiceImpl.getUserFromToken(token);

        Comments comment = commentRepository.findById(id).orElse(null);

        List<Comments> comments = commentRepository.findByReply(comment.getPost().getId(), comment.getId());

        if (comments == null || user == null) {
            return ApiResponse.builder()
                    .message("Something not found")
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        if (!comment.getUser().getId().equals(user.getId()) && !userServiceImpl.isAdmin(user)) {
            return ApiResponse.builder()
                    .message("Unauthorizated")
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        commentRepository.delete(comment);
        commentRepository.deleteAll(comments);

        return ApiResponse.builder()
                .message("Successfuly")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(buildCommentResponse(comment))
                .build();
    }

}
