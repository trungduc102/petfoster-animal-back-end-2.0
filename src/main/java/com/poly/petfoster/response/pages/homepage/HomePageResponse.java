package com.poly.petfoster.response.pages.homepage;

import java.util.List;

import com.poly.petfoster.response.pets.PetResponse;
import com.poly.petfoster.response.posts.PostResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HomePageResponse {
        private List<ImpactOfYearResponse> impactOfYear;
        private List<PetResponse> pets;
        private List<PostResponse> postsPreview;
}
