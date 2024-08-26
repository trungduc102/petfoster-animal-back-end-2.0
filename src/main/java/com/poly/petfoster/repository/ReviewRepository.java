package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
        @Query(nativeQuery = true, value = "select* from review where order_id= :orderId and product_id =:productId ")
        public Optional<Review> findbyOrderIdAndProductId(@Param("orderId") Integer orderId,
                        @Param("productId") String productId);

        @Query(nativeQuery = true, value = "select * from review where [user_id] = :userId and product_id = :productId and order_id = :orderId")
        public Optional<Review> findReviewByUserAndProduct(@Param("userId") String userId,
                        @Param("productId") String productId, @Param("orderId") Integer orderId);

        @Query(nativeQuery = true, value = "select * from review where [user_id] = :userId and product_id = :productId")
        public List<Review> findReviewByUserAndProductID(@Param("userId") String userId,
                        @Param("productId") String productId);

        @Query(nativeQuery = true, value = "select * from review where product_id = :productId and id not in (select distinct replied_id from review where replied_id is not null) and replied_id is null")
        public List<Review> getNoReplyReivewsByProduct(@Param("productId") String productId);

        @Query(nativeQuery = true, value = "select top 1 * from review where replied_id is null and product_id = :productId order by create_at desc")
        public Review getLastestReviewByProduct(@Param("productId") String productId);

        @Query(nativeQuery = true, value = "select * from review a " + //
                        "inner join product b on a.product_id = b.product_id " +
                        "where product_name like %:name%")
        public List<Product> findReviewByProductName(String name);

        @Query(nativeQuery = true, value = "select * from review where replied_id = :reviewId")
        public List<Review> getReplyReviews(@Param("reviewId") Integer reviewId);

        @Query("select r from Review r where r.product = :product order by r.createAt DESC")
        List<Review> findByProduct(@Param("product") Product product);
}
