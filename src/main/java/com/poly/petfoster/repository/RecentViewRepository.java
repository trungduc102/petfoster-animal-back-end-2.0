package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.RecentView;

public interface RecentViewRepository extends JpaRepository<RecentView, Integer> {

    @Query(nativeQuery = true, value = "select top 1.* from recent_view rv inner join users u on rv.[user_id] = u.[user_id] where rv.[user_id] =:userId and rv.product_id =:productId")
    public RecentView existByProductId(@Param("productId") String productId, @Param("userId") String userId);

    @Query(nativeQuery = true, value = "select * from recent_view rv inner join users u on rv.[user_id] = u.[user_id] where rv.[user_id] =:userId order by view_at desc")
    public List<RecentView> findAll(@Param("userId") String userId);

}
