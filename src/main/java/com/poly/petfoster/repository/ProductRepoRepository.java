package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.ProductRepo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Product;
import java.util.List;

public interface ProductRepoRepository extends JpaRepository<ProductRepo, Integer> {

    List<ProductRepo> findByProduct(Product product);

    @Query("select rp from ProductRepo rp where rp.isActive = true and rp.product.id = :productId order by rp.size asc")
    List<ProductRepo> findByProductSorting(@Param("productId") String idproduct);

    @Query("select rp.size from ProductRepo rp where rp.product.id = :productId order by rp.size asc")
    List<Integer> findSizeByProduct(@Param("productId") String productId);

    @Query(nativeQuery = true, value = "select top 1 * from product_repo where product_id = :productId order by size asc")
    ProductRepo findByProductMinRepo(@Param("productId") String productId);

    @Query(nativeQuery = true, value = "select top 1 * from product_repo where product_id = :productId order by out_price asc")
    ProductRepo findByProductMinPrice(@Param("productId") String productId);

    @Query(nativeQuery = true, value = "select * from product_repo where product_id = :productId and size = :size")
    public ProductRepo findProductRepoByIdAndSize(@Param("productId") String productId, @Param("size") Integer size);

    @Query(nativeQuery = true, value = "select * from product_repo where product_id = :productId")
    ProductRepo findByProductID(@Param("productId") String productId);
}
