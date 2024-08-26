package com.poly.petfoster.service.impl.carts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.CartItem;
import com.poly.petfoster.entity.Carts;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.CartItemRepository;
import com.poly.petfoster.repository.CartRepository;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.carts.CartRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.carts.CartResponse;
import com.poly.petfoster.service.carts.CartService;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepoRepository productRepoRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private PortUltil portUltil;

    @Override
    public ApiResponse updateCarts(String jwt, List<CartRequest> cartRequests) {
        // find user
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        // if user null
        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }

        Carts carts = cartRepository.findCarts(user.getId()).orElse(null);

        if (carts == null) {
            return ApiResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).errors(true).data(null)
                    .message(RespMessage.INTERNAL_SERVER_ERROR.getValue())
                    .build();
        }

        List<CartItem> cartItems = cartItemRepository.findByCartsId(carts.getCardId());

        if (cartItems.isEmpty()) {
            return ApiResponse.builder().status(200).errors(false).data(null).message("Update Successfully!")
                    .build();
        }

        // delete all outdate data
        cartItemRepository.deleteAll(cartItems);

        if (cartRequests.isEmpty()) {
            return ApiResponse.builder().status(200).errors(false).data(null).message("Update Successfully!")
                    .build();
        }

        List<CartItem> newItems = new ArrayList<>();

        cartRequests.stream().forEach(item -> {

            // if product repo not null
            ProductRepo productRepo = productRepoRepository.findProductRepoByIdAndSize(item.getId(),
                    item.getSize());

            if (productRepo == null) {
                return;
            }

            CartItem newItem = buildCartItem(item, carts, productRepo);

            newItems.add(newItem);
        });

        if (!newItems.isEmpty()) {
            cartItemRepository.saveAll(newItems);
        }

        List<CartResponse> cartResponses = new ArrayList<>();

        newItems.stream().forEach((item) -> {
            cartResponses.add(buildCartResponse(item.getProductRepo(), item.getQuantity()));
        });

        return ApiResponse.builder().status(200).errors(false).data(cartResponses).message("Update Successfully!")
                .build();
    }

    @Override
    public ApiResponse getCarts(String jwt) {

        // find user
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        // if user null
        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }
        Carts createCart = new Carts();

        Carts carts = cartRepository.findCarts(user.getId()).orElse(null);

        if (carts == null) {
            createCart.setUser(user);
            cartRepository.save(createCart);

            return ApiResponse.builder().message("No data available").status(200).errors(false).data(new ArrayList<>())
                    .build();
        }

        List<CartItem> cartItems = cartItemRepository.findByCartsId(carts.getCardId());

        if (cartItems.isEmpty()) {
            return ApiResponse.builder().message("No data available").status(200).errors(false).data(cartItems)
                    .build();
        }

        List<CartResponse> cartResponses = new ArrayList<>();

        for (int i = 0; i < cartItems.size(); i++) {
            CartResponse cartResponse = new CartResponse();
            cartResponse.setId(cartItems.get(i).getProductRepo().getProduct().getId());
            cartResponse.setBrand(cartItems.get(i).getProductRepo().getProduct().getBrand().getBrand());
            cartResponse.setSize(cartItems.get(i).getProductRepo().getSize());
            cartResponse.setImage(portUltil
                    .getUrlImage(cartItems.get(i).getProductRepo().getProduct().getImgs().get(0).getNameImg()));
            cartResponse.setName(cartItems.get(i).getProductRepo().getProduct().getName());
            cartResponse.setPrice(cartItems.get(i).getProductRepo().getOutPrice());
            cartResponse.setQuantity(cartItems.get(i).getQuantity());
            cartResponse.setRepo(cartItems.get(i).getProductRepo().getQuantity());
            cartResponses.add(cartResponse);
        }

        return ApiResponse.builder().status(200).errors(false).data(cartResponses)
                .build();
    }

    @Override
    public ApiResponse createCarts(String jwt, CartRequest cartRequest) {
        // find user
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        // if user null
        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }

        Carts carts = cartRepository.findCarts(user.getId()).orElse(null);

        if (carts == null) {
            carts = cartRepository.save(Carts.builder().user(user).build());
        }

        // if product repo
        ProductRepo productRepo = productRepoRepository.findProductRepoByIdAndSize(cartRequest.getId(),
                cartRequest.getSize());

        CartItem item = cartItemRepository
                .findBySizeAndProductId(carts.getCardId(), cartRequest.getId(), cartRequest.getSize()).orElse(null);

        if (item != null) {
            int newQuantity = item.getQuantity() + cartRequest.getQuantity();

            if (newQuantity > item.getProductRepo().getQuantity()) {
                item.setQuantity(item.getProductRepo().getQuantity());
            } else {
                item.setQuantity(newQuantity);
            }

            cartItemRepository.save(item);

            return ApiResponse.builder()
                    .message(RespMessage.SUCCESS.getValue())
                    .status(200).errors(false)
                    .data(buildCartResponse(productRepo, item.getQuantity()))
                    .build();
        }

        CartItem cartItem = CartItem.builder()
                .cart(carts)
                .quantity(cartRequest.getQuantity() > productRepo.getQuantity() ? productRepo.getQuantity()
                        : cartRequest.getQuantity())
                .productRepo(productRepo)
                .build();

        cartItemRepository.save(cartItem);

        return ApiResponse.builder().status(200).errors(false)
                .message(RespMessage.SUCCESS.getValue())
                .data(buildCartResponse(productRepo, cartItem.getQuantity()))
                .build();
    }

    private CartResponse buildCartResponse(ProductRepo productRepo, int newQuantity) {

        return CartResponse.builder()
                .id(productRepo.getProduct().getId())
                .brand(productRepo.getProduct().getBrand().getBrand())
                .size(productRepo.getSize())
                .image(portUltil.getUrlImage(productRepo.getProduct().getImgs().get(0).getNameImg()))
                .name(productRepo.getProduct().getName())
                .price(productRepo.getOutPrice())
                .quantity(newQuantity)
                .repo(productRepo.getQuantity())
                .build();
    }

    private CartItem buildCartItem(CartRequest cartRequest, Carts carts, ProductRepo productRepo) {

        return CartItem.builder()
                .cart(carts)
                .quantity(cartRequest.getQuantity() > productRepo.getQuantity() ? productRepo.getQuantity()
                        : cartRequest.getQuantity())
                .productRepo(productRepo)
                .build();
    }

}
