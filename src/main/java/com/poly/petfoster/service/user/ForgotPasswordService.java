package com.poly.petfoster.service.user;

import javax.servlet.http.HttpServletRequest;

import com.poly.petfoster.request.ResetPasswordRequest;
import com.poly.petfoster.response.ApiResponse;

public interface ForgotPasswordService {
    public ApiResponse sendCodeForResetPassword(HttpServletRequest req, ResetPasswordRequest resetPasswordRequest);

    public ApiResponse verifyConfirmResetPasswordEmail(String token);
}
