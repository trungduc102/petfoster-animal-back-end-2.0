package com.poly.petfoster.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public interface EmailService {

    public void sendVerificationEmail(HttpServletRequest req, String email, UUID otp);

    public void confirmResetPassword(HttpServletRequest req, String email, UUID token);
}
