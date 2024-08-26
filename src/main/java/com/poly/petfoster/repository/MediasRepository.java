package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.social.Medias;
import com.poly.petfoster.entity.social.Posts;

public interface MediasRepository extends JpaRepository<Medias, Integer> {

    @Query(value = "select m from Medias m where m.post = :post and m.index = :index")
    Medias findByIndex(@Param("post") Posts posts, @Param("index") Integer index);

    @Query(value = "select m from Medias m where m.post = :post order by m.index asc")
    List<Medias> findMediasWithPost(@Param("post") Posts posts);

    @Query(value = "select m from Medias m where m.name = :name ")
    Medias findByName(@Param("name") String name);

    @Query(value = "select m from Medias m where m.post = :post and m.isVideo = true")
    Medias exitsVideoOfPost(@Param("post") Posts post);

}
