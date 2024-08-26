package com.poly.petfoster.request.payments;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VnpaymentRequest {
    private HttpServletRequest httpServletRequest;

    private String idOrder;
    private Integer amouts;
    private String orderInfo;
}
