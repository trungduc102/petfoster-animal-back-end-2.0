package com.poly.petfoster.response.users;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartDataDetailUserResponse {
    private String title;
    private List<Integer> data;
}
