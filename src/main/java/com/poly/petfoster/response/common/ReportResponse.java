package com.poly.petfoster.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReportResponse<T> {
    private String title;
    private Double day;
    private Double month;
    private Double year;
}
