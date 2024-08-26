package com.poly.petfoster.response.order_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFilterResponse {

    private Integer orderId;

    private String username;

    private Integer total;

    private String placedDate;

    private String status;

    private Boolean read;

    private Integer print;

    private String token;

}
