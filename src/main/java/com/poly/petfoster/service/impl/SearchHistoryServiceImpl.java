package com.poly.petfoster.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.entity.SearchHistory;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.SearchHistoryRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.search_history.searchHistoryResponse;
import com.poly.petfoster.service.seach_history.SearchHistoryService;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {
    @Autowired
    SearchHistoryRepository searchHistoryRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserRepository userRepository;

    public ApiResponse getSeachHistopry(String jwt) {
        Map<String, String> errorsMap = new HashMap<>();
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            errorsMap.put("user", "user not found");
            return ApiResponse.builder()
                    .message("Unauthenrized")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errors(errorsMap).build();
        }

        List<searchHistoryResponse> listReponse = getListReponse(user.getId());

        return ApiResponse.builder()
                .message("Successfully")
                .status(HttpStatus.OK.value())
                .errors(null)
                .data(listReponse)
                .build();
    };

    public ApiResponse updateSeachHistopry(String jwt, String keyword) {
        Map<String, String> errorsMap = new HashMap<>();
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);
        if (user == null) {
            errorsMap.put("user", "user not found");
            return ApiResponse.builder()
                    .message("Unauthenrized")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errors(errorsMap).build();
        }
        if (keyword.isBlank()) {
            return ApiResponse.builder()
                    .message("Query product Successfully")
                    .status(HttpStatus.OK.value())
                    .errors(null)
                    .data(getListReponse(user.getId()))
                    .build();
        }
        List<SearchHistory> listSearch = searchHistoryRepository.FindByUserId(user.getId()).orElse(null);
        SearchHistory newSearch = SearchHistory.builder().keyword(keyword).user(user).build();

        if (listSearch.size() == 5) {
            searchHistoryRepository.delete(listSearch.get(4));
            searchHistoryRepository.save(newSearch);

        }
        for (SearchHistory itemHistory : listSearch) {
            if (itemHistory.getKeyword().equals(keyword)) {
                searchHistoryRepository.delete(itemHistory);
                searchHistoryRepository.save(newSearch);
            }
        }
        searchHistoryRepository.save(newSearch);
        List<searchHistoryResponse> listReponse = getListReponse(user.getId());

        return ApiResponse.builder()
                .message("Query product Successfully")
                .status(HttpStatus.OK.value())
                .errors(null)
                .data(listReponse)
                .build();
    };

    public ApiResponse deleteSeachHistopry(String jwt, String keyword) {
        Map<String, String> errorsMap = new HashMap<>();
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            errorsMap.put("user", "user not found");
            return ApiResponse.builder()
                    .message("Unauthenrized")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errors(errorsMap).build();
        }
        List<SearchHistory> listSearch = searchHistoryRepository.FindByUserIdAndKeyword(user.getId(), keyword)
                .orElse(null);
        searchHistoryRepository.deleteAll(listSearch);

        List<searchHistoryResponse> listReponse = getListReponse(user.getId());

        return ApiResponse.builder()
                .message("Query product Successfully")
                .status(HttpStatus.OK.value())
                .errors(null)
                .data(listReponse)
                .build();
    };

    public List<searchHistoryResponse> getListReponse(String id) {
        List<SearchHistory> listSearch = searchHistoryRepository.FindByUserId(id).orElse(null);

        List<searchHistoryResponse> listReponse = new ArrayList<searchHistoryResponse>();
        for (SearchHistory itemHistory : listSearch) {
            listReponse.add(new searchHistoryResponse(itemHistory.getId(), itemHistory.getKeyword()));
        }
        return listReponse;
    }
}
