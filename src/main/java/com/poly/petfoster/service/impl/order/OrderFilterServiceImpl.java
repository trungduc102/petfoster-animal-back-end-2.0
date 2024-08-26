package com.poly.petfoster.service.impl.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.Payment;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ShippingInfo;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.order_history.OrderDetailsResponse;
import com.poly.petfoster.response.order_history.OrderFilterResponse;
import com.poly.petfoster.response.order_history.OrderProductItem;
import com.poly.petfoster.response.order_history.OrdersFilterResponse;
import com.poly.petfoster.service.admin.order.OrderFilterService;
import com.poly.petfoster.ultils.FormatUtils;

@Service
public class OrderFilterServiceImpl implements OrderFilterService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    FormatUtils formatUtils;

    @Autowired
    OrderSeviceImpl orderSeviceImpl;

    @Override
    public ApiResponse filterOrders(Optional<String> username, Optional<Integer> orderId, Optional<String> status,
            Optional<Date> minDate, Optional<Date> maxDate, Optional<String> sort, Optional<Integer> page,
            Optional<Boolean> read) {

        Date minDateValue = minDate.orElse(null);
        Date maxDateValue = maxDate.orElse(null);

        if (minDateValue == null && maxDateValue != null) {
            minDateValue = maxDateValue;
        }

        if (maxDateValue == null && minDateValue != null) {
            maxDateValue = minDateValue;
        }

        if (minDateValue != null && maxDateValue != null) {
            if (minDateValue.after(maxDateValue)) {
                return ApiResponse.builder()
                        .message("The max date must after the min date!!!")
                        .status(HttpStatus.CONFLICT.value())
                        .errors("The max date must after the min date!!!")
                        .build();
            }
        }

        List<Orders> orders = ordersRepository.filterOrders(username.orElse(null), orderId.orElse(null),
                status.orElse(null), minDateValue, maxDateValue, sort.orElse(null));

        if (read.orElse(false)) {
            orders = ordersRepository.filterOrders(username.orElse(null), orderId.orElse(null),
                    status.orElse(null), minDateValue, maxDateValue, sort.orElse(null)).stream().filter(item -> {
                        return !item.getRead();
                    }).toList();
        }

        Pageable pageable = PageRequest.of(page.orElse(0), 10);

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), orders.size());

        if (startIndex > endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<Orders> visibleOrders = orders.subList(startIndex, endIndex);
        Page<Orders> pagination = new PageImpl<Orders>(visibleOrders, pageable, orders.size());

        List<OrderFilterResponse> orderFilters = new ArrayList<>();
        pagination.getContent().stream().forEach((order) -> {
            orderFilters.add(
                    OrderFilterResponse.builder()
                            .orderId(order.getId())
                            // .username(order.getUser().getUsername())
                            .username(order.getUser().getDisplayName() == null ? order.getUser().getUsername()
                                    : order.getUser().getDisplayName())
                            .total(order.getTotal().intValue())
                            .placedDate(formatUtils.dateToString(order.getCreateAt(), "yyyy-MM-dd"))
                            .status(order.getStatus())
                            .read(order.getRead())
                            .print(order.getPrint())
                            .token(order.getGhnCode())
                            .build());
        });

        return ApiResponse.builder()
                .message("Successfully!!!")
                .status(200)
                .errors(false)
                .data(OrdersFilterResponse.builder().orderFilters(orderFilters).pages(pagination.getTotalPages())
                        .build())
                .build();
    }

    @Override
    public ApiResponse orderDetails(Integer id) {

        Orders order = ordersRepository.findById(id).orElse(null);
        if (order == null) {
            return ApiResponse.builder()
                    .message("Order not found")
                    .status(404)
                    .errors("Order not found")
                    .build();
        }

        ShippingInfo shippingInfo = order.getShippingInfo();
        Payment payment = order.getPayment();

        List<OrderDetail> details = order.getOrderDetails();
        List<OrderProductItem> products = new ArrayList<>();
        details.forEach(item -> {
            products.add(orderSeviceImpl.createOrderProductItem(item));
        });

        OrderDetailsResponse orderDetails = OrderDetailsResponse.builder()

                .id(id)
                .address(formatUtils.getAddress(shippingInfo.getAddress(), shippingInfo.getWard(),
                        shippingInfo.getDistrict(),
                        shippingInfo.getProvince()))
                .placedDate(formatUtils.dateToString(order.getCreateAt(), "MMM d, yyyy"))
                .deliveryMethod(shippingInfo.getDeliveryCompany().getCompany())
                .name(shippingInfo.getFullName())
                .paymentMethod(payment.getPaymentMethod().getMethod())
                .phone(shippingInfo.getPhone())
                .products(products)
                .shippingFee(shippingInfo.getShipFee())
                .subTotal(order.getTotal().intValue())
                .description(order.getDescriptions() == null ? "" : order.getDescriptions())
                .total(order.getTotal().intValue() + shippingInfo.getShipFee())
                .state(order.getStatus())
                .expectedTime(order.getExpectedDeliveryTime())
                .username(order.getUser().getUsername())
                .displayName(order.getUser().getDisplayName())
                .print(order.getPrint())
                .read(order.getRead())
                .token(order.getGhnCode())
                .build();

        return ApiResponse.builder()
                .message("Successfully")
                .status(200)
                .errors(false)
                .data(orderDetails).build();

    }

}
