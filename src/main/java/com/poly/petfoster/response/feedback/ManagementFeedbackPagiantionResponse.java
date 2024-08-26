package com.poly.petfoster.response.feedback;

import com.poly.petfoster.response.common.PagiantionResponse;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ManagementFeedbackPagiantionResponse extends PagiantionResponse {
    private Integer total;

    public ManagementFeedbackPagiantionResponse(Object data, Integer pages, Integer total) {
        super(data, pages);
        this.total = total;
    }

}
