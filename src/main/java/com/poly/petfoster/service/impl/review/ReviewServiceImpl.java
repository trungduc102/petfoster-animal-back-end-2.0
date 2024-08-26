package com.poly.petfoster.service.impl.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.OrderStatus;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.Review;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.OrderDetailRepository;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.ReviewRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.review.ReviewRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.review.ReviewResponse;
import com.poly.petfoster.service.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

        @Autowired
        JwtProvider jwtProvider;

        @Autowired
        UserRepository userRepository;

        @Autowired
        OrdersRepository ordersRepository;

        @Autowired
        ProductRepository productRepository;

        @Autowired
        ReviewRepository reviewRepository;

        @Autowired
        OrderDetailRepository orderDetailRepository;

        public ApiResponse createReview(String jwt, ReviewRequest reviewRequest) {

                Map<String, String> errorsMap = new HashMap<>();
                User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

                if (user == null) {
                        errorsMap.put("user", "user not found!");
                        return ApiResponse.builder()
                                        .message("user not found!")
                                        .status(HttpStatus.UNAUTHORIZED.value())
                                        .errors(errorsMap).build();
                }

                Orders order = ordersRepository.findById(reviewRequest.getOrderId()).orElse(null);
                if (order == null) {
                        errorsMap.put("order", "order ID not found!");
                        return ApiResponse.builder()
                                        .message("order ID not found!")
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .errors(errorsMap).build();
                }
                // check order by user
                if (order.getUser() != user) {
                        errorsMap.put("order", "order is not conrect with user!");
                        return ApiResponse.builder()
                                        .message("Order is not conrect with user!")
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .data(null)
                                        .errors(errorsMap).build();
                }

                Product product = productRepository.findById(reviewRequest.getProductId()).orElse(null);
                if (product == null) {
                        errorsMap.put("product", "product ID not found!");
                        return ApiResponse.builder()
                                        .message("product ID not found!")
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .data(null)
                                        .errors(errorsMap).build();
                }

                // check product in order
                List<OrderDetail> listOrderDetail = order.getOrderDetails();

                if (listOrderDetail.stream().filter(item -> item.getProductRepo().getProduct() == product).toList()
                                .size() == 0) {
                        errorsMap.put("product", "The order doesn't have this product!");
                        return ApiResponse.builder()
                                        .message("The order doesn't have this product!")
                                        .status(HttpStatus.NOT_ACCEPTABLE.value())
                                        .data(null)
                                        .errors(errorsMap).build();
                }
                if(!order.getStatus().equalsIgnoreCase(OrderStatus.DELIVERED.getValue())){
                        errorsMap.put("order", "The order hasn't been delivered!");
                        return ApiResponse.builder()
                                        .message("The order hasn't been delivered!")
                                        .status(HttpStatus.NOT_ACCEPTABLE.value())
                                        .data(null)
                                        .errors(errorsMap).build();
                }

                Review review = reviewRepository
                                .findbyOrderIdAndProductId(reviewRequest.getOrderId(), reviewRequest.getProductId())
                                .orElse(null);
                if (review != null) {
                        errorsMap.put("review", "Review already exits!");
                        return ApiResponse.builder()
                                        .message("Review already exits!")
                                        .status(HttpStatus.FOUND.value())
                                        .errors(errorsMap).build();
                }
                Review newReview = Review.builder()
                                .product(product)
                                .order(order)
                                .user(user)
                                .comment(reviewRequest.getComment())
                                .rate(reviewRequest.getRate())
                                .build();
                reviewRepository.save(newReview);

                ReviewResponse reviewResponse = ReviewResponse.builder()
                                .productId(reviewRequest.getProductId())
                                .orderId(reviewRequest.getOrderId())
                                .comment(reviewRequest.getComment())
                                .rate(reviewRequest.getRate())
                                .build();

                return ApiResponse.builder()
                                .message("Review successfuly!!!")
                                .status(200)
                                .errors(false)
                                .data(reviewResponse)
                                .build();
        }

}
