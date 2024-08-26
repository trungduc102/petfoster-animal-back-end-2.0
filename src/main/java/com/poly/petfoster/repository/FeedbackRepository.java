package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.petfoster.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query(value = "select f from Feedback f ORDER BY f.id desc")
    List<Feedback> findAllReverse();
}
