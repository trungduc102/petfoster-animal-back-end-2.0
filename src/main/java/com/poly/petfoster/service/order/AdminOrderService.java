package com.poly.petfoster.service.order;

import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.response.ApiResponse;

public interface AdminOrderService {

    public ApiResponse updateOrderStatus(Integer id, UpdateStatusRequest updateStatusRequest);

    public ApiResponse updateReadForOrder(Integer id);

    public ApiResponse updatePrintForOrder(Integer id);

}
