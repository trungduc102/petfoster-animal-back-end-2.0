package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.social.Likes;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

    @Query(value = "select l from Likes l where l.user.id = :userId and l.post.id = :postId")
    Likes existByUserAndPost(@Param("userId") String userId, @Param("postId") Integer postId);
}
