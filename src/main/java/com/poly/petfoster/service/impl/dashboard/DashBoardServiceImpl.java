package com.poly.petfoster.service.impl.dashboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.ProductTypeRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.statistic.ProductRevenueByDate;
import com.poly.petfoster.response.statistic.ProductRevenueByDateResponse;
import com.poly.petfoster.response.statistic.Report;
import com.poly.petfoster.response.statistic.ReportResponse;
import com.poly.petfoster.response.statistic.SalesOverview;
import com.poly.petfoster.response.statistic.SalesOverviewsResponse;
import com.poly.petfoster.service.report.DashBoardService;
import com.poly.petfoster.ultils.FormatUtils;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    FormatUtils formatUtils;

    @Override
    public ApiResponse dailyReport() {

        return ApiResponse.builder()
                .message("Successfully!!!")
                .status(200)
                .errors(false)
                .data(ReportResponse.builder().reports(getReport()).build())
                .build();
    }

    @Override
    @Transactional
    public ApiResponse salesOverview(Optional<Integer> year) {

        Integer yearValue = year.orElse(null);

        if (yearValue == null || yearValue < 0) {
            yearValue = 2023;
        }

        return ApiResponse.builder()
                .message("Successfully!!!")
                .status(200)
                .errors(false)
                .data(
                        SalesOverviewsResponse.builder().salesOverview(
                                SalesOverview.builder().revenue(getRevenueByYear(yearValue))
                                        .productRevenueByType(getProductTypeRevenueByYear(yearValue)).build())
                                .build())
                .build();
    }

    @Override
    @Transactional
    public ApiResponse productRevenueByDate(Optional<Date> minDate, Optional<Date> maxDate) {

        Date minDateValue = minDate.orElse(null);
        Date maxDateValue = maxDate.orElse(null);

        if (minDateValue == null && maxDateValue != null) {
            minDateValue = maxDateValue;
        }

        if (maxDateValue == null && minDateValue != null) {
            maxDateValue = minDateValue;
        }

        if (maxDateValue == null && minDateValue == null) {
            // minDateValue = ordersRepository.getMinDate();
            // maxDateValue = ordersRepository.getMaxDate();

            minDateValue = formatUtils.dateToDateFormat(new Date(), "yyyy-MM-dd");
            maxDateValue = formatUtils.dateToDateFormat(new Date(), "yyyy-MM-dd");
        }

        if (minDateValue.after(maxDateValue)) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .message("Invalid date data")
                    .status(400)
                    .errors("Invalid date data")
                    .data(new ArrayList<>())
                    .build();
            return errorResponse;
        }

        List<Map<String, Object>> list = ordersRepository.getProductRevenueByDate(minDateValue, maxDateValue);

        return ApiResponse.builder().message("Successfully").status(200).errors(false)
                .data(
                        ProductRevenueByDateResponse.builder().productRevenueByDate(
                                ProductRevenueByDate.builder().data(list)
                                        .total(ordersRepository.getTotalRevenueByDate(minDateValue, maxDateValue))
                                        .build())
                                .build())
                .build();
    }

    public Report getReport() {

        Map<String, Integer> dailyRevenue = new HashMap<>();
        dailyRevenue.put("value", ordersRepository.getDailyRevenue());
        dailyRevenue.put("percentYesterday", ordersRepository.getYesterdayRevenue());

        Map<String, Integer> dailyOrders = new HashMap<>();
        dailyOrders.put("value", ordersRepository.getDailyOrder());
        dailyOrders.put("percentYesterday", ordersRepository.getYesterdayOrder());

        Map<String, Integer> users = new HashMap<>();
        users.put("value", userRepository.getTotalUsers());

        return Report.builder()
                .dailyRevenue(dailyRevenue)
                .dailyOrders(dailyOrders)
                .users(users)
                .build();
    }

    public Map<String, Object> getRevenueByYear(Integer year) {

        Map<String, Object> revenue = new HashMap<>();
        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct",
                "Nov", "Dec");
        revenue.put("categories", months);

        List<Integer> revenueMonths = ordersRepository.getRevenueByYear(year);
        Map<String, Object> dataList = new HashMap<>();
        dataList.put("name", "Revenue");
        dataList.put("data", revenueMonths);

        List<Map<String, Object>> data = new ArrayList<>();
        data.add(dataList);
        revenue.put("data", data);

        revenue.put("total", ordersRepository.getTotalRevenueByYear(year));

        return revenue;
    }

    public Map<String, Object> getProductTypeRevenueByYear(Integer year) {

        Map<String, Object> productRevenueByType = new HashMap<>();
        List<String> types = productTypeRepository.getProductTypeNames();
        productRevenueByType.put("categories", types);

        List<Integer> revenueProductType = ordersRepository.getProductTypeRevenueByYear(year);
        Map<String, Object> dataList = new HashMap<>();
        dataList.put("name", "Product Revenue By Type");
        dataList.put("data", revenueProductType);

        List<Map<String, Object>> data = new ArrayList<>();
        data.add(dataList);
        productRevenueByType.put("data", data);

        productRevenueByType.put("total", ordersRepository.getTotalRevenueByYear(year));

        return productRevenueByType;
    }

}
