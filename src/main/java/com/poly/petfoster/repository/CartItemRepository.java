package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(nativeQuery = true, value = "select * from cart_item where cart_id = :cartId")
    List<CartItem> findByCartsId(@Param("cartId") Integer cartId);

    @Query("select ci from CartItem ci where ci.productRepo.product.id = :productId and ci.productRepo.size = :size and ci.cart.id = :cartId")
    Optional<CartItem> findBySizeAndProductId(@Param("cartId") Integer cartId, @Param("productId") String productId,
            @Param("size") int size);
}
