package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.social.Comments;
import com.poly.petfoster.entity.social.Posts;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Integer> {

    @Query(nativeQuery = false, value = "select c from Comments c where c.replyTo is null and c.post = :post order by c.createAt desc")
    List<Comments> findByPost(Posts post);

    @Query(nativeQuery = true, value = "select * from comments " +
            "where comments.post_id = :postId and reply_to = :commentId")
    List<Comments> findByReply(@Param("postId") Integer postId, @Param("commentId") Integer commentId);
}
