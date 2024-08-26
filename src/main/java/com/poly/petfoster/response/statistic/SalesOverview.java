package com.poly.petfoster.response.statistic;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOverview {

    Map<String, Object> revenue;
    Map<String, Object> productRevenueByType;

}
