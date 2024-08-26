package com.poly.petfoster.response.takeaction;

import java.util.List;

import com.poly.petfoster.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewItem {

    private Integer id;

    private String avatar;

    private String name;

    private String displayName;

    private Integer rating;

    private List<Integer> sizes;

    private String comment;

    private String createAt;

    private List<ReviewItem> replayItems;

}
