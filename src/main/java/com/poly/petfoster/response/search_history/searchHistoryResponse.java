package com.poly.petfoster.response.search_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class searchHistoryResponse {
    Integer id;
    String title;
    
}
