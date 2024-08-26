package com.poly.petfoster.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

        // @Query("select sum(o.total) from Orders o where CONVERT(DATE, o.createAt) =
        // :date")
        // public Double getRevenueByDate(@Param("date") Date date);

        // @Query("select sum(o.total) from Orders o where MONTH(o.createAt) = :month")
        // public Double getRevenueByMonth(@Param("month") Integer month);

        @Query(nativeQuery = true, value = "select sum(total) from orders where convert(date, create_at) = convert(date, getdate()) and [status] not in ('Cancelled By Customer', 'Cancelled By Admin', 'Waiting')")
        public Integer getDailyRevenue();

        @Query(nativeQuery = true, value = "select sum(total) from orders where convert(date, create_at) = convert(date, getdate() - 1) and [status] not in ('Cancelled By Customer', 'Cancelled By Admin', 'Waiting')")
        public Integer getYesterdayRevenue();

        @Query(nativeQuery = true, value = "select count(*) from orders where convert(date, create_at) = convert(date, getdate()) and [status] not in ('Cancelled By Customer', 'Cancelled By Admin', 'Waiting')")
        public Integer getDailyOrder();

        @Query(nativeQuery = true, value = "select count(*) from orders where convert(date, create_at) = convert(date, getdate() - 1) and [status] not in ('Cancelled By Customer', 'Cancelled By Admin', 'Waiting')")
        public Integer getYesterdayOrder();

        @Procedure(name = "GetRevenueByYear")
        List<Integer> getRevenueByYear(@Param("year") Integer year);

        @Query(nativeQuery = true, value = "select sum(total) from orders where year(create_at) = :year and [status] not in ('Cancelled By Customer', 'Cancelled By Admin', 'Waiting')")
        public Integer getTotalRevenueByYear(@Param("year") Integer year);

        @Procedure(name = "GetProductTypeRevenueByYear")
        List<Integer> getProductTypeRevenueByYear(@Param("year") Integer year);

        @Query(nativeQuery = true, value = "select p.product_id as id, product_name as name, brand, size, sum(od.quantity) as quantity, sum(od.total) as revenue\r\n"
                        + //
                        "from product p " +
                        "inner join brand b on b.id = p.brand_id " +
                        "inner join product_repo pr on pr.product_id = p.product_id " +
                        "inner join order_detail od on od.product_repo_id = pr.product_repo_id " +
                        "inner join orders o on o.id = od.order_id " +
                        "where convert(date, o.create_at) between :minDate and :maxDate " +
                        "and [status] != 'cancelled' and [status] != 'waiting' " +
                        "group by p.product_id, product_name, brand, size")

        List<Map<String, Object>> getProductRevenueByDate(@Param("minDate") Date minDate,
                        @Param("maxDate") Date maxDate);

        @Query(nativeQuery = true, value = "select sum(total) from orders where convert(date, create_at) between :minDate and :maxDate and [status] != 'cancelled' and [status] != 'waiting'")
        public Integer getTotalRevenueByDate(@Param("minDate") Date minDate, @Param("maxDate") Date maxDate);

        @Query(nativeQuery = true, value = "select convert(date, min(create_at)) from orders")
        public Date getMinDate();

        @Query(nativeQuery = true, value = "select convert(date, max(create_at)) from orders")
        public Date getMaxDate();

        @Query(nativeQuery = true, value = "select * from orders o " +
                        "inner join shipping_info si on si.id = o.shipping_info_id " +
                        "where [user_id] = :userId and o.[status] like %:status% " +
                        "order by " +
                        "create_at desc")
        public List<Orders> orderHistory(@Param("userId") String userId, @Param("status") String status);

        @Query(nativeQuery = true, value = "select * from orders " +
                        "where [user_id] = :userId")
        public List<Orders> getOrderListByUserID(@Param("userId") String userId);

        @Query("SELECT o FROM Orders o " +
                        "WHERE (:username IS NULL OR o.user.username like %:username%) " +
                        "AND (:orderId IS NULL OR o.id = :orderId) " +
                        "AND (:status IS NULL OR o.status like %:status%) " +
                        "AND ((:minDate IS NULL AND :maxDate IS NULL) OR (convert(date, o.createAt) BETWEEN :minDate AND :maxDate)) "
                        +
                        "ORDER BY " +
                        "CASE WHEN :sort = 'total-desc' THEN o.total END DESC, " +
                        "CASE WHEN :sort = 'total-asc' THEN o.total END ASC, " +
                        "CASE WHEN :sort = 'id-desc' THEN o.id END DESC, " +
                        "CASE WHEN :sort = 'id-asc' THEN o.id END ASC, " +
                        "CASE WHEN :sort = 'date-desc' THEN o.createAt END DESC, " +
                        "CASE WHEN :sort = 'date-asc' THEN o.createAt END ASC, " +
                        "o.createAt DESC")
        List<Orders> filterOrders(
                        @Param("username") String username,
                        @Param("orderId") Integer orderId,
                        @Param("status") String status,
                        @Param("minDate") Date minDate,
                        @Param("maxDate") Date maxDate,
                        @Param("sort") String sort);

        @Query(value = "select COUNT(*) from orders where user_id = :userId and status = :status ", nativeQuery = true)
        public Integer countByStatusAndUserID(
                        @Param("userId") String date, @Param("status") String status);

        @Query(value = "select COUNT(*) from orders where user_id = :userId", nativeQuery = true)
        public Integer countByStatusAndUserID(
                        @Param("userId") String date);
}
