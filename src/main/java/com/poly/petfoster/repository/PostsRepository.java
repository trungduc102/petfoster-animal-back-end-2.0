package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.entity.social.Likes;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
        @Query(nativeQuery = true, value = "select top 4 * from posts")
        List<Posts> findAllByActive();

        @Query(value = "select * from posts " +
                        "where posts.id in (select top 5 p.id from posts p " +
                        "join likes l on l.post_id = p.id " +
                        "group by p.id " +
                        "order by COUNT(*) desc)", nativeQuery = true)
        List<Posts> hightlight();

        @Query(value = "select * from posts " +
                        "join users u on posts.[user_id] = u.[user_id] " +
                        "where posts.id in (select top 5 p.id from posts p " +
                        "join likes l on l.post_id = p.id " +
                        "group by p.id " +
                        "order by COUNT(*) desc) " +
                        "and u.username = :username " +
                        "order by posts.create_at desc", nativeQuery = true)
        List<Posts> hightlightOfUser(@Param("username") String username);

        @Query(value = "select p from Posts p " +
                        "where (:search IS NULL OR p.title LIKE %:search% OR p.id LIKE %:search% OR p.user.displayName LIKE %:search%)  "
                        +
                        "order by p.createAt desc")
        List<Posts> posts(@Param("search") Optional<String> search);

        @Query(value = "select p from Posts p where p.user.username = :username order by p.createAt desc")
        List<Posts> postsOfUser(@Param("username") String username);

        @Query(value = "select * from posts " +
                        "where id in ( " +
                        "select l.post_id from likes l " +
                        "join users u on u.[user_id] = l.[user_id] " +
                        "where u.username = :username " +
                        ") " +
                        "order by posts.create_at desc ", nativeQuery = true)
        List<Posts> postsLikeOfUser(@Param("username") String username);

        Posts findByUuid(String uuid);

        @Query(value = "select COUNT(*) from posts " +
                        "where user_id = :userId", nativeQuery = true)
        Integer countPostsByUSerID(@Param("userId") String userId);

        @Query(value = "select COUNT(*) from comments " +
                        "where user_id = :userId", nativeQuery = true)
        Integer countCommentsByUSerID(@Param("userId") String userId);

        @Query(value = "select COUNT(*) from likes " +
                        "where user_id = :userId", nativeQuery = true)
        Integer countLikesByUSerID(@Param("userId") String userId);

        @Query(value = "select COUNT(*) from liked_comments " +
                        "where user_id = :userId", nativeQuery = true)
        Integer countLikeCommentsByUSerID(@Param("userId") String userId);

}
