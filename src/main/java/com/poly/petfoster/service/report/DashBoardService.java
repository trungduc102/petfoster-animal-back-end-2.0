package com.poly.petfoster.service.report;

import java.util.Date;
import java.util.Optional;

import com.poly.petfoster.response.ApiResponse;

public interface DashBoardService {
    
    public ApiResponse dailyReport();

    public ApiResponse salesOverview(Optional<Integer> year);

    public ApiResponse productRevenueByDate(Optional<Date> minDate, Optional<Date> maxDate);

}
