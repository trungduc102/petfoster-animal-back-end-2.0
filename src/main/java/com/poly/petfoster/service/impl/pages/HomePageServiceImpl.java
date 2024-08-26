package com.poly.petfoster.service.impl.pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.repository.AdoptRepository;
import com.poly.petfoster.repository.DonateRepository;
import com.poly.petfoster.repository.FavoriteRepository;
import com.poly.petfoster.repository.PetRepository;
import com.poly.petfoster.repository.PostsRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.pages.homepage.HomePageResponse;
import com.poly.petfoster.response.pages.homepage.ImpactOfYearResponse;
import com.poly.petfoster.response.pets.PetResponse;
import com.poly.petfoster.response.posts.PostResponse;
import com.poly.petfoster.service.pages.HomePageService;
import com.poly.petfoster.service.pets.PetService;
import com.poly.petfoster.service.posts.PostService;

@Service
public class HomePageServiceImpl implements HomePageService {

        @Autowired
        private PetRepository petRepository;

        @Autowired
        private PetService petService;

        @Autowired
        private PostsRepository postsRepository;

        @Autowired
        private PostService postService;

        @Autowired
        private JwtProvider jwtProvider;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AdoptRepository adoptRepository;

        @Autowired
        private DonateRepository donateRepository;

        @Override
        public ApiResponse homepage() {

                // get token from headers. Can't use @RequestHeader("Authorization") because
                // when call api if have'nt token is throw error
                String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                                .getHeader("Authorization");

                // get pets raw data
                List<Pet> petsRaw = petRepository.findAllByActive();

                // get posts raw data
                List<Posts> postsRaw = postsRepository.findAllByActive();

                List<PostResponse> posts = postService.buildPostHomePageResponses(postsRaw);

                // get impact
                List<ImpactOfYearResponse> impacts = Arrays.asList(
                                new ImpactOfYearResponse("dog.svg", petRepository.findAll().size() + "",
                                                "Total pets fostered", null),
                                new ImpactOfYearResponse("cats.svg",
                                                donateRepository.getDonation() == null ? "0"
                                                                : donateRepository.getDonation() + "",
                                                "In products & donations", "$"),
                                new ImpactOfYearResponse("home-dog.svg", adoptRepository.getAdoptedPets().size() + "",
                                                "Total pets have a home", null));

                if (token != null) {

                        // get username from token requested to user
                        String username = jwtProvider.getUsernameFromToken(token);

                        // check username
                        if (username == null || username.isEmpty()) {
                                return ApiResponse.builder().message("Failure").status(HttpStatus.BAD_REQUEST.value())
                                                .errors(true)
                                                .data(new ArrayList<>()).build();
                        }

                        // get user to username
                        User user = userRepository.findByUsername(username).orElse(null);

                        // check user
                        if (user == null) {
                                return ApiResponse.builder().message("Failure").status(HttpStatus.BAD_REQUEST.value())
                                                .errors(true)
                                                .data(new ArrayList<>()).build();
                        }

                        // get pets
                        List<PetResponse> pets = petService.buildPetResponses(petsRaw, user);

                        return ApiResponse.builder().message("Successfuly").status(200).errors(false)
                                        .data(HomePageResponse.builder().impactOfYear(impacts).pets(pets)
                                                        .postsPreview(posts).build())
                                        .build();
                }

                List<PetResponse> pets = petService.buildPetResponses(petsRaw);

                return ApiResponse.builder().message("Successfuly").status(200).errors(false)
                                .data(HomePageResponse.builder().impactOfYear(impacts).pets(pets).postsPreview(posts)
                                                .build())
                                .build();
        }

}
