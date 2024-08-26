package com.poly.petfoster.service.impl.product_repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.entity.PriceChange;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.PriceChangeRepository;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.repository.CreateRepoRequest;
import com.poly.petfoster.request.repository.UpdateRepoRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.product_repo.ProductRepoService;

@Service
public class RepoProductServiceImpl implements ProductRepoService {

    @Autowired
    ProductRepoRepository productRepoRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PriceChangeRepository priceChangeRepository;

    @Override
    public ApiResponse updateOrCreateRepo(Integer id, UpdateRepoRequest updateRepoRequest, String token) {

        ProductRepo productRepo = productRepoRepository.findById(id).orElse(null);

        if (productRepo == null) {
            return ApiResponse.builder().message("Repo id is not exsist").status(404).errors(true).data(null).build();
        }

        // conditon when create a new price change is in price or out price of repo is
        // different with update data
        if (!productRepo.getInPrice().equals(updateRepoRequest.getInPrice())
                || !productRepo.getOutPrice().equals(updateRepoRequest.getOutPrice())) {
            // find user by token
            String username = jwtProvider.getUsernameFromToken(token);

            if (username == null) {
                return ApiResponse.builder().message("Username is not exsist").status(404).errors(true).data(null)
                        .build();
            }

            // find user
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                return ApiResponse.builder().message("User is not exsist").status(404).errors(true).data(null).build();
            }

            // befor set new value
            PriceChange priceChange = PriceChange.builder()
                    .user(user)
                    .productRepo(productRepo)
                    .oldInPrice(productRepo.getInPrice())
                    .oldOutPrice(productRepo.getOutPrice())
                    .newInPrice(updateRepoRequest.getInPrice())
                    .newOutPrice(updateRepoRequest.getOutPrice())
                    .build();

            priceChangeRepository.save(priceChange);

        }

        // after set new value
        productRepo.setInPrice(updateRepoRequest.getInPrice());
        productRepo.setOutPrice(updateRepoRequest.getOutPrice());
        productRepo.setQuantity(updateRepoRequest.getQuantity());

        productRepoRepository.save(productRepo);

        return ApiResponse.builder().message("Successfully").status(200).errors(false).data(productRepo).build();
    }

    @Override
    public ApiResponse deleteProductRepo(Integer id) {
        ProductRepo productRepo = productRepoRepository.findById(id).orElse(null);
        if (productRepo == null) {
            return ApiResponse.builder()
                    .message("Product repo not found !")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        productRepo.setIsActive(false);

        return ApiResponse.builder()
                .message("Delete success!")
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(productRepoRepository.save(productRepo))
                .build();
    }

    @Override
    public ApiResponse getProductRepositories(String idProduct) {
        if (idProduct.isBlank()) {
            return ApiResponse.builder()
                    .message("Product repo not found with " + idProduct)
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        List<ProductRepo> lProductRepos = productRepoRepository.findByProductSorting(idProduct);

        if (lProductRepos.isEmpty()) {
            return ApiResponse.builder()
                    .message("Product repo not found with " + idProduct)
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        return ApiResponse.builder().message("Successfully").status(200).errors(false).data(lProductRepos).build();
    }

    @Override
    public ApiResponse addAProductRepository(String idProduct, CreateRepoRequest createRepoRequest) {
        if (idProduct.isBlank()) {
            return ApiResponse.builder()
                    .message("Product not found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        Product product = productRepository.findById(idProduct).orElse(null);

        if (product == null) {
            return ApiResponse.builder()
                    .message("Product not found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        ProductRepo productRepo = ProductRepo.builder()
                .inStock(true)
                .product(product)
                .inPrice(createRepoRequest.getInPrice())
                .outPrice(createRepoRequest.getOutPrice())
                .quantity(createRepoRequest.getQuantity())
                .size(createRepoRequest.getSize())
                .isActive(true)
                .build();

        return ApiResponse.builder().message("Successfully").status(200).errors(false)
                .data(productRepoRepository.save(productRepo)).build();
    }

}
