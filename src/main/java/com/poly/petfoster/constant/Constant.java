package com.poly.petfoster.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Constant {

    public static final String CLIENT_URL = "http://localhost:3000/";
    public static final String SECRET_KEY = "dfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhfdfhdsfjhjdfjsdhf";
    public static final String JWT_HEADER = "Authorization";
    public static final Integer TOKEN_EXPIRE_LIMIT = 5 * 60 * 1000;
    public static final String BASE_URL = "http://localhost:3000/login/";
    public static final String CLIENT_BASE_URL = "http://localhost:3000/";
    // public static final String BASE_URL = "http://localhost:8019/api/";

    // vn pay
    public static String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String VNP_RETURN_URL = "http://localhost:3000/payment";
    public static String VNP_TMN_CODE = "EWMX8UAJ";
    public static String VNP_SECRET_KEY = "YGFMMZSPJSYYKBXRJPBPOSOEWZUEGKPF";
    public static String VNP_API_ARL = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    public static Date MIN_DATE = new Date(0);

    // Giao hang nhanh
    public static String GHN_TOKEN = "539096b5-8f95-11ee-a6e6-e60958111f48";
    public static String GHN_SHOPID = "190419";
    public static String GHN_CREATE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";
    public static String GHN_GETPROVINCE = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
    public static String GHN_GETDISCTRICT = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
    public static String GHN_GETWARD = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id";
    public static String GHN_CANCEL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel";

    public static List<String> ACCEPT_EXTENTION = new ArrayList<>(Arrays.asList(
            "svg",
            "webp", "jpg", "png", "mp4"));
}
