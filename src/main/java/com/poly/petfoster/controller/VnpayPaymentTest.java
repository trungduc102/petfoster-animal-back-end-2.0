package com.poly.petfoster.controller;

import java.io.ObjectInputFilter.Config;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.request.payments.VnpaymentRequest;
import com.poly.petfoster.response.AuthResponse;
import com.poly.petfoster.ultils.VnpayUltils;

@RestController
public class VnpayPaymentTest {

    @Autowired
    HttpServletRequest req;

    @PostMapping("/api/test/payment")
    public ResponseEntity<AuthResponse> payment()
            throws UnsupportedEncodingException {

        String paymentUrl = VnpayUltils
                .getVnpayPayment(VnpaymentRequest.builder()
                        .httpServletRequest(req)
                        .amouts(20000).build());

        return ResponseEntity.ok(AuthResponse.builder().token(paymentUrl).message("success").build());
    }
}
