package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {

    @Query("SELECT s FROM SearchHistory s WHERE s.user.id = :userId ORDER BY s.searchAt DESC")
    public Optional<List<SearchHistory>> FindByUserId(@Param("userId") String userId);
    @Query("SELECT s FROM SearchHistory s WHERE s.user.id = :userId AND s.keyword = :keyword")
    public Optional<List<SearchHistory>> FindByUserIdAndKeyword(@Param("userId") String userId,@Param("keyword") String keyword);
    

}
