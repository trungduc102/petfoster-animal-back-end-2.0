package com.poly.petfoster.service.impl.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.product_manage.ProductManageResponse;
import com.poly.petfoster.response.productfilter.ProductFilterResponse;
import com.poly.petfoster.response.takeaction.ProductItem;
import com.poly.petfoster.service.impl.TakeActionServiceImpl;
import com.poly.petfoster.service.product.ProductFilterService;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class ProductFilterServiceImpl implements ProductFilterService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TakeActionServiceImpl takeActionServiceImpl;

    @Autowired
    private ProductRepoRepository productRepoRepository;

    @Autowired
    private PortUltil portUltil;

    @Override
    public ApiResponse filterProducts(Optional<String> typeName, Optional<Double> minPrice, Optional<Double> maxPrice,
            Optional<Boolean> stock, Optional<String> brand, Optional<String> productName, Optional<String> sort,
            Optional<Integer> page) {

        List<ProductItem> productItems = new ArrayList<>();
        List<Product> filterProducts = productRepository.filterProducts(typeName.orElse(null), minPrice.orElse(null),
                maxPrice.orElse(null), stock.orElse(null), brand.orElse(null), productName.orElse(null),
                sort.orElse(null));
        Pageable pageable = PageRequest.of(page.orElse(0), 9);

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), filterProducts.size());

        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<Product> visibleProducts = filterProducts.subList(startIndex, endIndex);
        Page<Product> pagination = new PageImpl<Product>(visibleProducts, pageable, filterProducts.size());

        pagination.getContent().stream().forEach((product) -> {
            productItems.add(takeActionServiceImpl.createProductTakeAction(product));
        });

        return ApiResponse.builder().message("Successfully!")
                .status(200)
                .errors(false)
                .data(
                        ProductFilterResponse.builder().filterProducts(productItems)
                                .pages(pagination.getTotalPages())
                                .build())
                .build();
    }

    public ApiResponse filterAdminProducts(Optional<String> keyword, Optional<String> typeName, Optional<String> brand,
            Optional<String> sort, Optional<Integer> page, Boolean isActive) {
        List<ProductManageResponse> productItems = new ArrayList<>();

        List<Product> products = productRepository.filterAdminProducts(keyword.orElse(null), typeName.orElse(null),
                brand.orElse(null), sort.orElse(null), isActive);

        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(PagiantionResponse.builder().data(new ArrayList<>())
                            .pages(0).build())
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<Product> visibleProducts = products.subList(startIndex, endIndex);

        visibleProducts.stream().forEach(product -> {
            productItems.add(ProductManageResponse.builder()
                    .id(product.getId())
                    .image(portUltil.getUrlImage(product.getImgs().get(0).getNameImg()))
                    .brand(product.getBrand().getBrand())
                    .name(product.getName())
                    .type(product.getProductType().getName())
                    .repo(productRepoRepository.findByProduct(product))
                    .build());
        });

        Page<ProductManageResponse> pagination = new PageImpl<ProductManageResponse>(productItems, pageable,
                products.size());

        return ApiResponse.builder()
                .message("Query product Successfully")
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(PagiantionResponse.builder().data(pagination.getContent())
                        .pages(pagination.getTotalPages()).build())
                .build();
    };

}
