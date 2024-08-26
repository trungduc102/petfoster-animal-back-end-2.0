package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.social.LikedComments;
import com.poly.petfoster.entity.social.Likes;

public interface LikeCommentRepository extends JpaRepository<LikedComments, Integer> {

    @Query(value = "select l from LikedComments l where l.user.id = :userId and l.comment.id = :commentId")
    LikedComments existByUserAndComment(@Param("userId") String userId, @Param("commentId") Integer commentId);
}
