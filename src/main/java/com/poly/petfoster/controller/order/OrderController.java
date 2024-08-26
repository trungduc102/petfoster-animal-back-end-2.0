package com.poly.petfoster.controller.order;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.order.OrderRequest;
import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.request.payments.PaymentRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.order.OrderService;

@RestController
@RequestMapping("/api/user/")
public class OrderController {
    
    @Autowired
    OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<ApiResponse> order(@RequestHeader("Authorization") String jwt, @Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.order(jwt, orderRequest));
    }

    @GetMapping("order/history")
    public ResponseEntity<ApiResponse> ordersHistory(
            @RequestHeader("Authorization") String jwt, 
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("status") Optional<String> status) {
        return ResponseEntity.ok(orderService.orderHistory(jwt, page, status));
    }

    @PostMapping("payment")
    public ResponseEntity<ApiResponse> payment(@Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(orderService.payment(paymentRequest));
    }

    @GetMapping("order/history/{id}")
    public ResponseEntity<ApiResponse> orderDetails(@RequestHeader("Authorization") String jwt, @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.orderDetails(jwt, id));
    }

    @PostMapping("order/cancel/{id}")
    public ResponseEntity<ApiResponse> cancelOrder(@RequestHeader("Authorization") String jwt, @PathVariable Integer id, @RequestBody UpdateStatusRequest updateStatusRequest) {
        return ResponseEntity.ok(orderService.cancelOrder(jwt, id, updateStatusRequest));
    }
}
