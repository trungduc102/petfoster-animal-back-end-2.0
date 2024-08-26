package com.poly.petfoster.service.impl.recent_view_product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.RecentView;
import com.poly.petfoster.entity.Review;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.RecentViewRepository;
import com.poly.petfoster.repository.ReviewRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.recent_view_product.RecentViewReponse;
import com.poly.petfoster.service.recent_view_products.RecentViewService;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class RecentViewServiceImpl implements RecentViewService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    RecentViewRepository recentViewRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    PortUltil portUltil;

    @Override
    public ApiResponse getRecentView(String jwt) {
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);
        Map<String, String> errorsMap = new HashMap<>();
        if (user == null) {
            errorsMap.put("user", "user not found");
            return ApiResponse.builder()
                    .message("Unauthenrized")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errors(errorsMap).build();
        }

        List<RecentView> recentViews = recentViewRepository.findAll(user.getId());
        List<RecentViewReponse> recentViewReponses = new ArrayList<>();
        for (RecentView recentView : recentViews) {
            Integer[] sizes = new Integer[recentView.getProduct().getProductsRepo().size()];
            if (recentView.getProduct().getProductsRepo().size() > 0) {
                for (int i = 0; i < recentView.getProduct().getProductsRepo().size(); i++) {
                    sizes[i] = recentView.getProduct().getProductsRepo().get(i).getSize();
                }
            } else {
                sizes = null;
            }

            List<Review> reviews = reviewRepository.findReviewByUserAndProductID(user.getId(),
                    recentView.getProduct().getId());
            RecentViewReponse recentViewReponse = RecentViewReponse.builder().id(recentView.getProduct().getId())
                    .brand(recentView.getProduct().getBrand().getBrand())
                    .size(sizes)
                    .discount(10)
                    .image(portUltil.getUrlImage(recentView.getProduct().getImgs().get(0).getNameImg()))
                    .name(recentView.getProduct().getName())
                    .oldPrice(recentView.getProduct().getProductsRepo().get(0).getInPrice())
                    .price(recentView.getProduct().getProductsRepo().get(0).getInPrice())
                    .rating(reviews.isEmpty() ? null : reviews.get(0).getRate())
                    .build();
            recentViewReponses.add(recentViewReponse);
        }

        return ApiResponse.builder().data(recentViewReponses).status(200)
                .message("Successfully get_all_recent_view").build();
    }

    @Override
    public ApiResponse putRecentView(String jwt, String producrId) {
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);
        Map<String, String> errorsMap = new HashMap<>();
        if (user == null) {
            errorsMap.put("user", "user not found");
            return ApiResponse.builder()
                    .message("Unauthenrized")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errors(errorsMap).build();
        }
        Product getProduct = productRepository.findById(producrId).orElse(null);
        if (getProduct == null) {
            return ApiResponse.builder().data(null).status(400)
                    .message("ProductId not exist").build();
        }

        List<RecentView> recentViews = recentViewRepository.findAll(user.getId());
        RecentView existRecentView = recentViewRepository.existByProductId(producrId, user.getId());
        if (recentViews.size() > 10 && existRecentView != null) {
            for (int i = 10; i < recentViews.size(); i++) {
                recentViewRepository.delete(recentViews.get(i));
                recentViews = recentViewRepository.findAll(user.getId());
            }
        }
        if (recentViews.size() >= 10 && existRecentView == null) {
            for (int i = 9; i < recentViews.size(); i++) {
                recentViewRepository.delete(recentViews.get(i));
                recentViews = recentViewRepository.findAll(user.getId());
            }
        }

        RecentView recentView = new RecentView(existRecentView != null ? existRecentView.getId() : null, new Date(),
                user, getProduct);
        recentViews.add(recentView);

        for (RecentView recentView2 : recentViews) {
            recentViewRepository.save(recentView2);
        }

        return ApiResponse.builder().data(getRecentView(jwt).getData()).status(200)
                .message("Update recent view successfully!").build();

    }
}
