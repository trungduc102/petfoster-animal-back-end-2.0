package com.poly.petfoster.controller.admin.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.petfoster.response.order_history.OrderDetailsResponse;
import com.poly.petfoster.service.order.OrderService;

@Controller
@RequestMapping("/api/admin/orders")
public class OrdersController {
    @Autowired
    OrderService orderService;

    @GetMapping("")
    public String getOrderTable(Model model) {
        List<OrderDetailsResponse> orderDetailsList = orderService.orderDetailsTable("All");
        model.addAttribute("list", orderDetailsList);
        return "orders";
    }

}
