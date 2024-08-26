package com.poly.petfoster.controller.admin.orders;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.order.OrderFilterService;
import com.poly.petfoster.service.order.AdminOrderService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/admin/orders/")
public class AdminOrderController {

    @Autowired
    AdminOrderService adminOrderService;

    @Autowired
    OrderFilterService orderFilterService;

    @PostMapping("/status/{id}")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Integer id,
            @RequestBody UpdateStatusRequest updateStatusRequest) {
        return ResponseEntity.ok(adminOrderService.updateOrderStatus(id, updateStatusRequest));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> filterOrders(
            @RequestParam("username") Optional<String> username,
            @RequestParam("orderId") Optional<Integer> orderId,
            @RequestParam("status") Optional<String> status,
            @RequestParam("minDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> minDate,
            @RequestParam("maxDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> maxDate,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("read") Optional<Boolean> read) {
        return ResponseEntity
                .ok(orderFilterService.filterOrders(username, orderId, status, minDate, maxDate, sort, page, read));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse> orderDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(orderFilterService.orderDetails(id));
    }

    @PutMapping("read/{id}")
    public ResponseEntity<ApiResponse> updateReadForOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(adminOrderService.updateReadForOrder(id));
    }

    @PutMapping("print/{id}")
    public ResponseEntity<ApiResponse> updatePrintForOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(adminOrderService.updatePrintForOrder(id));
    }

}
