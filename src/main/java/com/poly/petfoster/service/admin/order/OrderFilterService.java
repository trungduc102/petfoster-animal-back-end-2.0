package com.poly.petfoster.service.admin.order;

import java.util.Date;
import java.util.Optional;

import com.poly.petfoster.response.ApiResponse;

public interface OrderFilterService {

    ApiResponse filterOrders(Optional<String> username, Optional<Integer> orderId, Optional<String> status,
            Optional<Date> minDate, Optional<Date> maxDate, Optional<String> sort, Optional<Integer> page,
            Optional<Boolean> read);

    ApiResponse orderDetails(Integer id);

}
