package com.poly.petfoster.controller.admin.orders;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.petfoster.response.order_history.OrderDetailsResponse;
import com.poly.petfoster.service.order.OrderService;

@Controller
@RequestMapping("/api/orders")
public class OrderPrintInvoiceController {
    @Autowired
    OrderService orderService;

    @GetMapping("/print/{id}")
    public String printInvoice(Model model, @PathVariable("id") Integer id, HttpServletResponse response) {
        OrderDetailsResponse orderDetailsResponse = orderService.printInvoice(id);

        response.setHeader("X-Frame-Options", "ALLOWALL");
        response.setHeader("TOKEN", "123");

        model.addAttribute("data", orderDetailsResponse);
        return "invoice";
    }
}
