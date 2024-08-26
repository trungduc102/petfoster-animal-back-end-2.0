package com.poly.petfoster.service.impl.price_changes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.PriceChange;
import com.poly.petfoster.repository.PriceChangeRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.price_changes.PriceChangeResponse;
import com.poly.petfoster.service.admin.price_changes.PriceChangeService;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class PriceChangeServiceImpl implements PriceChangeService {

    @Autowired
    private PriceChangeRepository priceChangeRepository;

    @Autowired
    private PortUltil portUltil;

    @Override
    public ApiResponse getPriceChange(String idProduct) {

        System.out.println(idProduct);

        if (idProduct.isBlank()) {
            return ApiResponse.builder()
                    .message("Product not found !!!")
                    .status(412)
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<PriceChange> priceChanges = priceChangeRepository.findByProductId(idProduct);

        List<PriceChangeResponse> priceChangeResponses = new ArrayList<>();

        if (!priceChanges.isEmpty()) {
            priceChanges.forEach(item -> {
                priceChangeResponses.add(buildPriceChangeResponse(item));
            });
        }

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(200)
                .errors(false)
                .data(priceChangeResponses)
                .build();
    }

    private PriceChangeResponse buildPriceChangeResponse(PriceChange priceChange) {
        Map<String, String> user = new HashMap<>();

        user.put("id", priceChange.getUser().getId());
        user.put("fullname", priceChange.getUser().getFullname() == null ? priceChange.getUser().getUsername()
                : priceChange.getUser().getFullname());
        user.put("avartar", portUltil.getUrlImage(priceChange.getUser().getAvatar()));

        return PriceChangeResponse.builder()
                .id(priceChange.getId())
                .newInPrice(priceChange.getNewInPrice())
                .newOutPrice(priceChange.getNewOutPrice())
                .oldInPrice(priceChange.getOldInPrice())
                .oldOutPrice(priceChange.getOldOutPrice())
                .updateAt(priceChange.getUpdateAt())
                .size(priceChange.getProductRepo().getSize())
                .user(user)
                .build();
    }

}
