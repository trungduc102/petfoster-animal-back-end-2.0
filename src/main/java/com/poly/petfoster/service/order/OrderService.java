package com.poly.petfoster.service.order;

import java.util.List;
import java.util.Optional;

import com.poly.petfoster.request.order.OrderRequest;
import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.request.payments.PaymentRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.order_history.OrderDetailsResponse;

public interface OrderService {

    public ApiResponse order(String jwt, OrderRequest orderRequest);

    public ApiResponse orderHistory(String jwt, Optional<Integer> page, Optional<String> status);

    public ApiResponse payment(PaymentRequest paymentRequest);

    public ApiResponse orderDetails(String jwt, Integer id);

    public ApiResponse cancelOrder(String jwt, Integer id, UpdateStatusRequest updateStatusRequest);

    public List<OrderDetailsResponse> orderDetailsTable(String username);

    public OrderDetailsResponse printInvoice(Integer id);

}
