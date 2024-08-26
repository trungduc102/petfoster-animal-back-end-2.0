package com.poly.petfoster.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.poly.petfoster.request.ResetPasswordRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.user.ForgotPasswordService;

@Controller
@RequestMapping("/api/")
public class ForgotPasswordController {
    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(HttpServletRequest req,
            @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(forgotPasswordService.sendCodeForResetPassword(req, resetPasswordRequest));
    }

    @PostMapping("verify-forgot")
    public ResponseEntity<ApiResponse> verifyForgot(@RequestPart("code") String code) {
        return ResponseEntity.ok(forgotPasswordService.verifyConfirmResetPasswordEmail(code));
    }
}
