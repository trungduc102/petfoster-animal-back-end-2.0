package com.poly.petfoster.service.impl.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nimbusds.openid.connect.sdk.claims.Address;
import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.constant.OrderStatus;
import com.poly.petfoster.constant.PatternExpression;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Addresses;
import com.poly.petfoster.entity.DeliveryCompany;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Orders;
import com.poly.petfoster.entity.Payment;
import com.poly.petfoster.entity.PaymentMethod;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.Review;
import com.poly.petfoster.entity.ShippingInfo;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.AddressRepository;
import com.poly.petfoster.repository.DeliveryCompanyRepository;
import com.poly.petfoster.repository.OrderDetailRepository;
import com.poly.petfoster.repository.OrdersRepository;
import com.poly.petfoster.repository.PaymentMethodRepository;
import com.poly.petfoster.repository.PaymentRepository;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.ReviewRepository;
import com.poly.petfoster.repository.ShippingInfoRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.order.OrderItem;
import com.poly.petfoster.request.order.OrderRequest;
import com.poly.petfoster.request.order.UpdateStatusRequest;
import com.poly.petfoster.request.payments.PaymentRequest;
import com.poly.petfoster.request.payments.VnpaymentRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.order.OrderResponse;
import com.poly.petfoster.response.order_history.OrderDetailsResponse;
import com.poly.petfoster.response.order_history.OrderFilterResponse;
import com.poly.petfoster.response.order_history.OrderHistory;
import com.poly.petfoster.response.order_history.OrderHistoryResponse;
import com.poly.petfoster.response.order_history.OrderProductItem;
import com.poly.petfoster.service.impl.TakeActionServiceImpl;
import com.poly.petfoster.service.order.OrderService;
import com.poly.petfoster.ultils.FormatUtils;
import com.poly.petfoster.ultils.GiaoHangNhanhUltils;
import com.poly.petfoster.ultils.PortUltil;
import com.poly.petfoster.ultils.VnpayUltils;

@Service
public class OrderSeviceImpl implements OrderService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PortUltil portUltil;

    @Autowired
    FormatUtils formatUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShippingInfoRepository shippingInfoRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductRepoRepository productRepoRepository;

    @Autowired
    TakeActionServiceImpl takeActionServiceImpl;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    VnpayUltils vnpayUltils;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    DeliveryCompanyRepository deliveryCompanyRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    GiaoHangNhanhUltils giaoHangNhanhUltils;

    @Override
    public ApiResponse order(String jwt, OrderRequest orderRequest) {

        Double total = 0.0;
        Map<String, String> errorsMap = new HashMap<>();
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            errorsMap.put("user", "user not found");
            return ApiResponse.builder()
                    .message("Unauthenrized")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errors(errorsMap).build();
        }

        Addresses address = addressRepository.findById(orderRequest.getAddressId()).orElse(null);
        if (address == null) {
            errorsMap.put("address", "address not found");
            return ApiResponse.builder()
                    .message("address not found")
                    .status(404)
                    .errors(errorsMap).build();
        }

        if (user.getAddresses().indexOf(address) == -1) {
            errorsMap.put("address", "This address not found in address list of this user");
            return ApiResponse.builder()
                    .message("This address not found in address list of this user")
                    .status(404)
                    .errors(errorsMap).build();
        }

        ShippingInfo shippingInfo = this.createShippingInfo(address, orderRequest);

        DeliveryCompany deliveryCompany = deliveryCompanyRepository.findById(orderRequest.getDeliveryId()).orElse(null);
        if (deliveryCompany == null) {
            errorsMap.put("Delivery method", "Delivery method not found");
            return ApiResponse.builder()
                    .message("Delivery method not found")
                    .status(404)
                    .errors(errorsMap).build();
        }

        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderRequest.getMethodId()).orElse(null);
        if (paymentMethod == null) {
            errorsMap.put("payment method", "payment method not found");
            return ApiResponse.builder()
                    .message("payment method is not support")
                    .status(404)
                    .errors(errorsMap).build();
        }
        Payment payment = this.createPayment(orderRequest);

        Orders order = this.createOrder(address, payment, shippingInfo);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderItem orderItem : orderRequest.getOrderItems()) {
            ProductRepo productRepo = productRepoRepository.findProductRepoByIdAndSize(orderItem.getProductId(),
                    orderItem.getSize());

            if (orderItem.getQuantity() <= 0) {
                errorsMap.put("quantity", "quantity must larger than 0");
                return ApiResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("quantity must larger than 0")
                        .errors(errorsMap)
                        .build();
            }

            if (productRepo.getQuantity() < orderItem.getQuantity()) {
                errorsMap.put("quantity", "quantity are not enought, please try another one!!!");
                return ApiResponse.builder()
                        .message(HttpStatus.BAD_REQUEST.toString())
                        .errors(errorsMap)
                        .build();
            }

            OrderDetail orderDetail = this.crateOrderDetails(productRepo, orderItem, order);
            orderDetails.add(orderDetail);

            total += orderDetail.getTotal();
        }

        order.setTotal(total);
        order.setOrderDetails(orderDetails);
        ordersRepository.save(order);

        payment.setAmount(order.getTotal() + shippingInfo.getShipFee());
        paymentRepository.save(payment);

        String paymentUrl;

        if (orderRequest.getMethodId() == 1) {
            order.setStatus(OrderStatus.PLACED.getValue());

            for (OrderDetail orderDetail : orderDetails) {
                ProductRepo productRepo = orderDetail.getProductRepo();
                this.updateQuantity(productRepo, orderDetail.getQuantity());
            }

            if (deliveryCompany.getId() == 2) {
                ApiResponse apiResponse = giaoHangNhanhUltils.create(order);
                if (apiResponse.getErrors().equals(true)) {
                    return apiResponse;
                }

                if (apiResponse.getStatus() == 400) {
                    return apiResponse;
                }
            }

            return ApiResponse.builder()
                    .message("order successfuly!!!")
                    .status(200)
                    .errors(false)
                    .data(order.getId())
                    .build();
        } else {
            try {
                paymentUrl = VnpayUltils.getVnpayPayment(VnpaymentRequest.builder().idOrder(order.getId().toString())
                        .httpServletRequest(httpServletRequest).amouts(payment.getAmount().intValue()).build());
                order.setStatus(OrderStatus.WAITING.getValue());
                ordersRepository.save(order);

            } catch (Exception e) {
                return ApiResponse.builder()
                        .message("Unsupported encoding exception")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errors(false)
                        .build();
            }

            return ApiResponse.builder()
                    .message("order successfuly!!!")
                    .status(200)
                    .errors(false)
                    .data(paymentUrl).build();
        }
    }

    @Override
    public ApiResponse orderHistory(String jwt, Optional<Integer> page, Optional<String> status) {

        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }

        List<Orders> ordersHistory = ordersRepository.orderHistory(user.getId(), status.orElse(""));

        if (ordersHistory.isEmpty()) {
            return ApiResponse.builder().message("No data available").status(200).errors(false).data(ordersHistory)
                    .build();
        }

        List<OrderHistory> data = new ArrayList<>();

        for (Orders order : ordersHistory) {

            List<OrderDetail> orderDetails = order.getOrderDetails();
            List<OrderProductItem> products = new ArrayList<>();

            Boolean isTotalRate = true;
            for (OrderDetail orderDetail : orderDetails) {
                OrderProductItem orderProductItem = createOrderProductItem(orderDetail);
                products.add(orderProductItem);
                isTotalRate = isTotalRate && orderProductItem.getIsRate();
            }

            OrderHistory orderHistory = OrderHistory.builder()
                    .id(order.getId())
                    .datePlace(formatUtils.dateToString(order.getCreateAt(), "MMM d, yyyy"))
                    .state(order.getStatus())
                    .stateMessage(order.getStatus())
                    .total(order.getTotal() + order.getShippingInfo().getShipFee())
                    .products(products)
                    .isTotalRate(isTotalRate)
                    .build();

            data.add(orderHistory);
        }

        Pageable pageable = PageRequest.of(page.orElse(0), 8);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), data.size());

        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<OrderHistory> visibleProducts = data.subList(startIndex, endIndex);
        Page<OrderHistory> pagination = new PageImpl<OrderHistory>(visibleProducts, pageable, data.size());

        return ApiResponse.builder()
                .message("Successfully")
                .status(200).errors(false).data(OrderHistoryResponse.builder().data(pagination.getContent())
                        .pages(pagination.getTotalPages()).build())
                .build();
    }

    @Override
    public ApiResponse payment(PaymentRequest paymentRequest) {

        Map<String, String> errorMap = new HashMap<>();

        Orders order = ordersRepository.findById(paymentRequest.getOrderId()).orElse(null);

        if (order == null) {
            errorMap.put("order", "order not found");
            return ApiResponse.builder()
                    .message("order not found")
                    .status(404)
                    .errors(errorMap).build();
        }

        Payment payment = order.getPayment();

        if (paymentRequest.getIsPaid()) {
            payment.setAmount(paymentRequest.getAmount().doubleValue());
            payment.setIsPaid(paymentRequest.getIsPaid());

            try {
                payment.setPayAt(formatUtils.convertMilisecondsToDate(paymentRequest.getPayAt()));
            } catch (Exception e) {
                errorMap.put("payAt", "Number Format Exception");
                return ApiResponse.builder()
                        .message("Number Format Exception")
                        .status(HttpStatus.CONFLICT.value())
                        .errors(errorMap)
                        .data(null).build();
            }

            payment.setTransactionNumber(paymentRequest.getTransactionNumber().toString());
            paymentRepository.save(payment);

            order.setStatus(OrderStatus.PLACED.getValue());

            ordersRepository.save(order);

            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                ProductRepo productRepo = orderDetail.getProductRepo();
                this.updateQuantity(productRepo, orderDetail.getQuantity());
            }

            if (order.getShippingInfo().getDeliveryCompany().getId() == 2) {
                ApiResponse apiResponse = giaoHangNhanhUltils.create(order);
                if (apiResponse.getErrors().equals(true)) {
                    return apiResponse;
                }
            }

            return ApiResponse.builder()
                    .message("order successfuly!!!")
                    .status(200)
                    .errors(false)
                    .data(OrderResponse.builder().orderId(order.getId()).photourl(portUltil.getUrlImage(
                            order.getOrderDetails().get(0).getProductRepo().getProduct().getImgs().get(0).getNameImg()))
                            .build())
                    .build();
        }

        return ApiResponse.builder()
                .message("Transaction failure!!! Please try again")
                .status(HttpStatus.FAILED_DEPENDENCY.value())
                .errors(true)
                .data(null).build();
    }

    @Override
    public ApiResponse orderDetails(String jwt, Integer id) {

        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);

        if (user == null) {
            return ApiResponse.builder()
                    .message("Invalid Token!!!")
                    .status(400)
                    .errors("Invalid token")
                    .build();
        }

        Orders order = ordersRepository.findById(id).orElse(null);
        if (order == null) {
            return ApiResponse.builder()
                    .message("Order not found")
                    .status(404)
                    .errors("Order not found")
                    .build();
        }

        if (user.getOrders().indexOf(order) == -1) {
            return ApiResponse.builder()
                    .message("This order not found in order list of this user")
                    .status(404)
                    .errors("This order not found in order list of this user").build();
        }

        ShippingInfo shippingInfo = order.getShippingInfo();
        Payment payment = order.getPayment();

        List<OrderDetail> details = order.getOrderDetails();
        List<OrderProductItem> products = new ArrayList<>();
        details.forEach(item -> {
            products.add(this.createOrderProductItem(item));
        });

        OrderDetailsResponse orderDetails = OrderDetailsResponse.builder()
                .id(id)
                .address(formatUtils.getAddress(shippingInfo.getAddress(), shippingInfo.getWard(),
                        shippingInfo.getDistrict(),
                        shippingInfo.getProvince()))
                .placedDate(formatUtils.dateToString(order.getCreateAt(), "MMM d, yyyy"))
                .deliveryMethod(shippingInfo.getDeliveryCompany().getCompany())
                .name(shippingInfo.getFullName())
                .paymentMethod(payment.getPaymentMethod().getMethod())
                .phone(shippingInfo.getPhone())
                .products(products)
                .shippingFee(shippingInfo.getShipFee())
                .subTotal(order.getTotal().intValue())
                .total(order.getTotal().intValue() + shippingInfo.getShipFee())
                .state(order.getStatus())
                .expectedTime(order.getExpectedDeliveryTime())
                .build();

        return ApiResponse.builder()
                .message("Successfully")
                .status(200)
                .errors(false)
                .data(orderDetails).build();
    }

    public OrderProductItem createOrderProductItem(OrderDetail orderDetail) {
        String image = "";

        if (!orderDetail.getProductRepo().getProduct().getImgs().isEmpty()) {
            image = orderDetail.getProductRepo().getProduct().getImgs().get(0).getNameImg();
        }

        Review review = reviewRepository.findReviewByUserAndProduct(orderDetail.getOrder().getUser().getId(),
                orderDetail.getProductRepo().getProduct().getId(), orderDetail.getOrder().getId()).orElse(null);

        return OrderProductItem
                .builder()
                .id(orderDetail.getProductRepo().getProduct().getId())
                .size(orderDetail.getProductRepo().getSize())
                .image(portUltil.getUrlImage(image))
                .name(orderDetail.getProductRepo().getProduct().getName())
                .brand(orderDetail.getProductRepo().getProduct().getBrand().getBrand())
                .price(orderDetail.getProductRepo().getOutPrice().intValue())
                .quantity(orderDetail.getQuantity())
                .isRate(review != null)
                .repo(orderDetail.getProductRepo().getQuantity())
                .build();
    }

    private ShippingInfo createShippingInfo(Addresses address, OrderRequest orderRequest) {
        return shippingInfoRepository.save(ShippingInfo.builder()
                .fullName(address.getRecipient())
                .province(address.getProvince())
                .district(address.getDistrict())
                .ward(address.getWard())
                .address(address.getAddress())
                .phone(address.getPhone())
                .shipFee(orderRequest.getShip())
                .deliveryCompany(deliveryCompanyRepository.findById(orderRequest.getDeliveryId()).get())
                .build());
    }

    private OrderDetail crateOrderDetails(ProductRepo productRepo, OrderItem orderItem, Orders order) {
        return orderDetailRepository.save(OrderDetail.builder()
                .price(productRepo.getOutPrice())
                .quantity(orderItem.getQuantity())
                .total(productRepo.getOutPrice() * orderItem.getQuantity())
                .order(order)
                .productRepo(productRepo)
                .build());
    }

    private Payment createPayment(OrderRequest orderRequest) {
        return paymentRepository.save(Payment.builder()
                .isPaid(false)
                .paymentMethod(paymentMethodRepository.findById(orderRequest.getMethodId()).get())
                .build());
    }

    private Orders createOrder(Addresses address, Payment payment, ShippingInfo shippingInfo) {
        return ordersRepository.save(Orders.builder()
                .user(address.getUser())
                .payment(payment)
                .read(false)
                .print(0)
                .shippingInfo(shippingInfo)
                .build());
    }

    private ProductRepo updateQuantity(ProductRepo productRepo, Integer quantity) {
        productRepo.setQuantity(productRepo.getQuantity() - quantity);
        return productRepoRepository.save(productRepo);
    }

    public ProductRepo returnQuantity(ProductRepo productRepo, Integer quantity) {
        productRepo.setQuantity(productRepo.getQuantity() + quantity);
        return productRepoRepository.save(productRepo);
    }

    @Override
    public ApiResponse cancelOrder(String jwt, Integer id, UpdateStatusRequest updateStatusRequest) {

        Map<String, String> errorsMap = new HashMap<>();
        User user = userRepository.findByUsername(jwtProvider.getUsernameFromToken(jwt)).orElse(null);
        if (user == null) {
            errorsMap.put("user", "user not found");
            return ApiResponse.builder()
                    .message("Unauthenrized")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .data(null)
                    .errors(errorsMap).build();
        }

        Orders order = ordersRepository.findById(id).orElse(null);
        if (order == null) {
            return ApiResponse.builder()
                    .message("order not found")
                    .status(404)
                    .errors(true)
                    .data(null)
                    .build();
        }

        if (user.getOrders().indexOf(order) == -1) {
            return ApiResponse.builder()
                    .message("This order not found in order list of this user")
                    .status(HttpStatus.FAILED_DEPENDENCY.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        String updateStatus;
        try {
            updateStatus = OrderStatus.valueOf(updateStatusRequest.getStatus()).getValue();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message(updateStatusRequest.getStatus() + " doesn't exists in the enum")
                    .status(404)
                    .errors(true)
                    .build();
        }

        if (!updateStatus.equalsIgnoreCase(OrderStatus.CANCELLED_BY_CUSTOMER.getValue())) {
            return ApiResponse.builder()
                    .message("You cannot update the order!!!")
                    .status(HttpStatus.FAILED_DEPENDENCY.value())
                    .errors(true)
                    .build();
        }

        if (order.getStatus().equalsIgnoreCase(OrderStatus.SHIPPING.getValue())
                || order.getStatus().equalsIgnoreCase(OrderStatus.DELIVERED.getValue())) {
            return ApiResponse.builder()
                    .message("Cannot cancel the order!!!")
                    .status(HttpStatus.FAILED_DEPENDENCY.value())
                    .errors(true)
                    .data(null)
                    .build();
        }

        order.setStatus(updateStatus);
        order.setDescriptions(updateStatusRequest.getReason() != null ? updateStatusRequest.getReason() : "");
        ordersRepository.save(order);

        if (order.getGhnCode() != null) {
            List<String> order_codes = new ArrayList<>();
            RestTemplate restTemplate = new RestTemplate();
            order_codes.add(order.getGhnCode());
            HttpEntity<Map<String, Object>> request = giaoHangNhanhUltils.createRequest("order_codes", order_codes);
            ResponseEntity<String> response = restTemplate.postForEntity(Constant.GHN_CANCEL, request, String.class);
        }

        for (OrderDetail orderDetail : order.getOrderDetails()) {
            this.returnQuantity(orderDetail.getProductRepo(), orderDetail.getQuantity());
        }

        return ApiResponse.builder()
                .message("Successfully!!!")
                .status(200)
                .errors(false)
                .data(null)
                .build();
    }

    public List<OrderDetailsResponse> orderDetailsTable(String userID) {

        List<Orders> orderList = new ArrayList<>();

        User user = userRepository.findByUsername(userID).orElse(null);
        if (user != null) {
            orderList = ordersRepository.getOrderListByUserID(user.getId());
        }

        orderList = ordersRepository.findAll();
        if (orderList.isEmpty()) {
            return null;
        }

        List<OrderDetailsResponse> oDetailsList = new ArrayList<>();
        for (Orders order : orderList) {
            ShippingInfo shippingInfo = order.getShippingInfo();
            Payment payment = order.getPayment();
            List<OrderDetail> details = order.getOrderDetails();
            List<OrderProductItem> products = new ArrayList<>();
            details.forEach(item -> {
                products.add(this.createOrderProductItem(item));
            });

            OrderDetailsResponse orderDetails = new OrderDetailsResponse();
            orderDetails.setId(order.getShippingInfo().getId());
            orderDetails.setAddress(formatUtils.getAddress(shippingInfo.getAddress(), shippingInfo.getWard(),
                    shippingInfo.getDistrict(), shippingInfo.getProvince()));
            orderDetails.setPlacedDate(order.getCreateAt().toString());
            orderDetails.setDeliveryMethod(shippingInfo.getDeliveryCompany().getCompany());
            orderDetails.setName(shippingInfo.getFullName());
            orderDetails.setPaymentMethod(payment.getPaymentMethod().getMethod());
            orderDetails.setPhone(shippingInfo.getPhone());
            orderDetails.setProducts(products);
            orderDetails.setShippingFee(shippingInfo.getShipFee());
            orderDetails.setSubTotal(order.getTotal().intValue());
            orderDetails.setTotal(order.getTotal().intValue() + shippingInfo.getShipFee());
            orderDetails.setState(order.getStatus());
            orderDetails.setQuantity(order.getOrderDetails().get(0).getQuantity());
            oDetailsList.add(orderDetails);
        }

        return oDetailsList;
    }

    @Override
    public OrderDetailsResponse printInvoice(Integer id) {
        Orders orders = ordersRepository.findById(id).orElse(null);

        if (orders == null)
            return null;

        // set print
        orders.setPrint(orders.getPrint() + 1);

        ordersRepository.save(orders);

        ShippingInfo shippingInfo = orders.getShippingInfo();

        Payment payment = orders.getPayment();
        List<OrderDetail> details = orders.getOrderDetails();
        List<OrderProductItem> products = new ArrayList<>();
        details.forEach(item -> {
            products.add(this.createOrderProductItem(item));
        });

        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setId(orders.getId());
        orderDetailsResponse.setAddress(formatUtils.getAddress(shippingInfo.getAddress(), shippingInfo.getWard(),
                shippingInfo.getDistrict(), shippingInfo.getProvince()));
        orderDetailsResponse.setPlacedDate(formatUtils.dateToString(orders.getCreateAt(), "dd/MM/yyyy HH:mm"));
        orderDetailsResponse.setDeliveryMethod(shippingInfo.getDeliveryCompany().getCompany());
        orderDetailsResponse.setName(shippingInfo.getFullName());
        orderDetailsResponse.setPaymentMethod(payment.getPaymentMethod().getMethod());
        orderDetailsResponse.setPhone(shippingInfo.getPhone());
        orderDetailsResponse.setProducts(products);
        orderDetailsResponse.setShippingFee(shippingInfo.getShipFee());
        orderDetailsResponse.setSubTotal(orders.getTotal().intValue());
        orderDetailsResponse.setTotal(orders.getTotal().intValue() + shippingInfo.getShipFee());
        orderDetailsResponse.setState(orders.getStatus());
        orderDetailsResponse.setQuantity(orders.getOrderDetails().get(0).getQuantity());
        orderDetailsResponse.setDisplayName(orders.getUser().getFullname() == null ? orders.getUser().getDisplayName()
                : orders.getUser().getFullname());
        return orderDetailsResponse;
    }

}
