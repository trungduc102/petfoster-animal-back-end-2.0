package com.poly.petfoster.service.impl.product;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Brand;
import com.poly.petfoster.entity.Imgs;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.ProductType;
import com.poly.petfoster.repository.BrandRepository;
import com.poly.petfoster.repository.ImagesRepository;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.ProductTypeRepository;
import com.poly.petfoster.request.CreateProductRequest;
import com.poly.petfoster.request.ProductRepoRequest;
import com.poly.petfoster.request.product.ProductInfoRequest;
import com.poly.petfoster.request.product.ProductRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.product_manage.ProductDetailManageResponse;
import com.poly.petfoster.response.product_manage.ProductInfoResponse;
import com.poly.petfoster.response.product_manage.ProductManageResponse;
import com.poly.petfoster.service.admin.product.ProductService;
import com.poly.petfoster.ultils.ImageUtils;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class ProductServiceImpl implements ProductService {
        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private ProductRepoRepository productRepoRepository;

        @Autowired
        private ProductTypeRepository productTypeRepository;

        @Autowired
        private PortUltil portUltil;

        @Autowired
        private ImagesRepository imagesRepository;

        @Autowired
        private BrandRepository brandRepository;

        @Override
        public ApiResponse getAllProduct(Optional<Integer> page) {

                List<ProductManageResponse> productItems = new ArrayList<>();
                List<Product> products = productRepository.findAll();

                Pageable pageable = PageRequest.of(page.orElse(0), 10);
                int startIndex = (int) pageable.getOffset();
                int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

                if (startIndex >= endIndex) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(null)
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
        }

        @Override
        public ApiResponse updateProduct(String id, ProductRequest productRequest) {
                Map<String, String> errorsMap = new HashMap<>();

                if (!productRepository.existsById(id)) {
                        return ApiResponse.builder()
                                        .message("Can't find Product ID")
                                        .status(404)
                                        .errors(errorsMap)
                                        .data(null)
                                        .build();
                }

                Product selectProduct = productRepository.findById(id).orElse(null);

                if (selectProduct == null) {
                        return ApiResponse.builder()
                                        .message("Can't find Product ID")
                                        .status(404)
                                        .errors(errorsMap)
                                        .data(null)
                                        .build();
                }

                if (!productRequest.getProductsRepo().isEmpty()) {
                        selectProduct.setProductsRepo(productRequest.getProductsRepo());

                        productRequest.getProductsRepo().stream().forEach(item -> {
                                item.setProduct(selectProduct);
                        });

                        productRequest.getProductsRepo().stream().forEach(item -> {

                                if (item.getId() == null) {
                                        productRepoRepository.save(item);
                                } else {
                                        if (!productRepoRepository.existsById(item.getId())) {
                                                productRepoRepository.save(item);
                                        }
                                }

                        });

                }

                ProductType type = productTypeRepository.findById(productRequest.getProductType()).orElse(null);

                if (type != null) {
                        selectProduct.setProductType(type);
                }

                selectProduct.setName(productRequest.getName());
                selectProduct.setBrand(productRequest.getBrand());
                selectProduct.setDesc(productRequest.getDesc());

                // Product selectProduct = productRepository.findById(id).orElse(null);

                return ApiResponse.builder()
                                .message("Query product Successfully")
                                .status(HttpStatus.OK.value())
                                .errors(null)
                                .data(productRepository.save(selectProduct))
                                .build();
        };

        @Override
        public ApiResponse deleteProduct(String id) {

                if (!productRepository.existsById(id))
                        return ApiResponse.builder()
                                        .message("Can't found Product ID")
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .errors(null)
                                        .data(null)
                                        .build();

                Product selectProduct = productRepository.findById(id).orElse(null);

                if (selectProduct == null) {
                        return ApiResponse.builder()
                                        .message("Can't found Product ID")
                                        .status(HttpStatus.OK.value())
                                        .errors(null)
                                        .data(null)
                                        .build();
                }

                // set active
                selectProduct.setIsActive(false);

                return ApiResponse.builder()
                                .message("Query product Successfully")
                                .status(HttpStatus.OK.value())
                                .errors(null)
                                .data(productRepository.save(selectProduct))
                                .build();
        }

        @Override
        public ApiResponse getProduct(String id) {

                Product selectProduct = productRepository.findById(id).orElse(null);

                // List<Product> list = new ArrayList<>();
                // productRepository.save(null);
                // list.add(productRepository.findAll());
                if (selectProduct == null) {
                        return ApiResponse.builder()
                                        .message("Can't found Product ID")
                                        .status(HttpStatus.OK.value())
                                        .errors(null)
                                        .data(null)
                                        .build();
                }
                ProductDetailManageResponse data = ProductDetailManageResponse.builder()
                                .id(selectProduct.getId())
                                .name(selectProduct.getName())
                                .description(selectProduct.getDesc())
                                .brand(selectProduct.getBrand().getBrand())
                                .type(selectProduct.getProductType().getName())
                                .repo(selectProduct.getProductsRepo())
                                .images(selectProduct.getImgs().stream().map((image) -> {
                                        Map<String, String> imageObj = new HashMap<>();
                                        imageObj.put("image", portUltil.getUrlImage(image.getNameImg()));
                                        imageObj.put("id", image.getId().toString());
                                        return imageObj;
                                }))
                                .build();

                return ApiResponse.builder()
                                .message("Query product Successfully")
                                .status(HttpStatus.OK.value())
                                .errors(null)
                                .data(data)
                                .build();
        }

        @Override
        public ApiResponse getProductInfo(String id) {

                if (id.isEmpty()) {
                        return ApiResponse.builder()
                                        .message("Id invalid")
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .errors(true)
                                        .data(null)
                                        .build();
                }

                Product selectProduct = productRepository.findById(id).orElse(null);

                if (selectProduct == null) {
                        return ApiResponse.builder()
                                        .message("Can't found product by id")
                                        .status(404)
                                        .errors(true)
                                        .data(null)
                                        .build();
                }

                return ApiResponse.builder()
                                .message("Successfuly !")
                                .status(HttpStatus.OK.value())
                                .errors(false)
                                .data(ProductInfoResponse.builder()
                                                .id(selectProduct.getId())
                                                .name(selectProduct.getName())
                                                .brand(selectProduct.getBrand().getId() + "")
                                                .type(selectProduct.getProductType().getId())
                                                .description(selectProduct.getDesc())
                                                .build())
                                .build();
        }

        @Override
        public ApiResponse updateProductWithInfo(String id, ProductInfoRequest productInfoRequest) {

                System.out.println(id);
                System.out.println(productInfoRequest);

                if (id.isEmpty() || !id.equals(productInfoRequest.getId())) {
                        return ApiResponse.builder()
                                        .message("Id invalid")
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .errors(true)
                                        .data(null)
                                        .build();
                }

                Product selectProduct = productRepository.findById(id).orElse(null);

                if (selectProduct == null) {
                        return ApiResponse.builder()
                                        .message("Can't found product by id")
                                        .status(404)
                                        .errors(true)
                                        .data(null)
                                        .build();
                }

                // find brand
                Brand brand = brandRepository.findById(productInfoRequest.getBrand()).orElse(null);

                if (brand == null) {
                        return ApiResponse.builder()
                                        .message("Can't found brand by " + productInfoRequest.getBrand())
                                        .status(404)
                                        .errors(true)
                                        .data(null)
                                        .build();
                }

                // all good
                selectProduct.setBrand(brand);
                selectProduct.setProductType(getNewTypeForProduct(productInfoRequest.getType(), selectProduct));
                selectProduct.setName(productInfoRequest.getName());
                selectProduct.setDesc(productInfoRequest.getDescription());

                return ApiResponse.builder()
                                .message("Update product successfully")
                                .status(HttpStatus.OK.value())
                                .errors(null)
                                .data(productRepository.save(selectProduct))
                                .build();
        };

        public ProductType getNewTypeForProduct(String idType, Product product) {

                if (idType.equals(product.getProductType().getId())) {
                        return product.getProductType();
                }

                ProductType newType = productTypeRepository.findById(idType).orElse(null);

                if (newType == null) {
                        return product.getProductType();
                }

                return newType;
        }

        public ProductType getNewTypeForProduct(String idType) {
                ProductType newType = productTypeRepository.findById(idType).orElse(null);
                return newType;
        }

        @Override
        public ApiResponse createProduct(CreateProductRequest createProductRequest, List<MultipartFile> images) {

                List<Product> products = productRepository.findAllNoActive();

                // find brand
                Brand brand = brandRepository.findById(createProductRequest.getBrand()).orElse(null);

                if (brand == null) {
                        return ApiResponse.builder()
                                        .message("Can't found brand by " + createProductRequest.getBrand())
                                        .status(404)
                                        .errors(true)
                                        .data(null)
                                        .build();
                }

                Product product = Product.builder()
                                .id(getNextId(products.get(products.size() - 1).getId()))
                                .brand(brand)
                                .name(createProductRequest.getName())
                                .isActive(true)
                                .desc(createProductRequest.getDescription())
                                .productType(getNewTypeForProduct(createProductRequest.getType()))
                                .build();

                productRepository.save(product);

                ProductType productType = productTypeRepository.findById(createProductRequest.getType()).orElse(null);
                if (productType == null) {
                        return ApiResponse.builder().message("Type id is not exists").status(404)
                                        .errors("PRODUCT_TYPE_NOT_FOUND").data(null).build();
                }

                List<ProductRepo> repos = new ArrayList<>();
                for (ProductRepoRequest productRepo : createProductRequest.getRepo()) {

                        ProductRepo repo = ProductRepo.builder()
                                        .size(productRepo.getSize())
                                        .inPrice(productRepo.getInPrice().doubleValue())
                                        .outPrice(productRepo.getOutPrice().doubleValue())
                                        .quantity(productRepo.getQuantity())
                                        .inStock(true)
                                        .product(product)
                                        .isActive(true)
                                        .build();
                        productRepoRepository.save(repo);
                        repos.add(repo);
                }

                // save images
                List<Imgs> newListImages = images.stream().map((image) -> {

                        try {
                                File file = ImageUtils.createFileImage();

                                image.transferTo(new File(file.getAbsolutePath()));
                                Imgs newImages = Imgs.builder()
                                                .nameImg(file.getName())
                                                .product(product)
                                                .build();

                                return newImages;

                        } catch (Exception e) {
                                System.out.println(e.getMessage());
                                return null;

                        }
                }).toList();

                imagesRepository.saveAll(newListImages);
                product.setProductType(productType);
                product.setProductsRepo(repos);
                product.setImgs(newListImages);

                return ApiResponse.builder().message("Successfully").status(200).errors(false).data(product).build();
        }

        public String getNextId(String lastId) {

                String nextId = "";
                String first = lastId.substring(0, 2);
                String last = lastId.substring(2);
                Integer number = Integer.parseInt(last);
                Double log = Math.log10(number);

                if (log < 1) {
                        nextId = first + "000" + ++number;
                } else if (log < 2) {
                        nextId = first + "00" + ++number;
                } else if (log < 3) {
                        nextId = first + "0" + ++number;
                } else {
                        nextId = first + ++number;
                }

                return nextId;
        }

}
