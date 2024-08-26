package com.poly.petfoster.response.statistic;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    Map<String, Integer> dailyOrders;

    Map<String, Integer> dailyRevenue;

    Map<String, Integer> users;

}
