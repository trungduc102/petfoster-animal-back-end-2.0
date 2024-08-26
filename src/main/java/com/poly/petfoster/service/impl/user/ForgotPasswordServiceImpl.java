package com.poly.petfoster.service.impl.user;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.User;
import com.poly.petfoster.random.RandomPassword;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.ResetPasswordRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.impl.EmailServiceImpl;
import com.poly.petfoster.service.user.ForgotPasswordService;
import com.poly.petfoster.ultils.MailUtils;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailUtils mailUtils;
    @Autowired
    EmailServiceImpl emailServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RandomPassword randomPassword;

    @Override
    public ApiResponse sendCodeForResetPassword(HttpServletRequest req, ResetPasswordRequest resetPasswordRequest) {
        // start validate
        // check exist user
        User existsUser = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElse(null);
        if (existsUser == null) {
            return ApiResponse.builder().data("").message("user is not exist").status(404)
                    .errors(false).build();
        }

        // check active user
        if (existsUser.getIsActive() == false) {
            return ApiResponse.builder().data("").message("user is not active").status(404)
                    .errors(false).build();
        }

        // check email verification
        if (existsUser.getIsEmailVerified() == false) {
            return ApiResponse.builder().data("").message("email has not been verified").status(404)
                    .errors(false).build();
        }

        // end validate

        // verify code to email
        String email = existsUser.getEmail();
        String token = sendToken(req, email).toString();
        existsUser.setToken(token);
        userRepository.save(existsUser);
        return ApiResponse.builder().data("the reset password code has been sent via email !").message("Successfully!")
                .status(200)
                .errors(true).build();
    }

    @Override
    public ApiResponse verifyConfirmResetPasswordEmail(String token) {
        // TODO Auto-generated method stub
        User existsUser = userRepository.findByToken(token);

        if (existsUser == null) {
            return ApiResponse.builder()
                    .message("Token is not exists")
                    .status(404)
                    .errors(true)
                    .build();
        }

        // if (new Date().getTime() - existsUser.getTokenCreateAt().getTime() >
        // Constant.TOKEN_EXPIRE_LIMIT) {
        // return ApiResponse.builder()
        // .message("Token is expired")
        // .status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value())
        // .errors(true)
        // .build();
        // }

        // random new password
        String newPassword = randomPassword.randomPassword();
        String newPasswordEncode = passwordEncoder.encode(newPassword);
        existsUser.setPassword(newPasswordEncode);

        // save to database
        userRepository.save(existsUser);

        // send password to mail
        String toEmail = existsUser.getEmail();
        String subject = "Reset password!";
        String body = "Hello " + existsUser.getFullname() +
                "\n You are performing a password update, your new password is " + newPassword;
        mailUtils.sendEmail(toEmail, subject, body);
        return ApiResponse.builder().data(existsUser).message("Your password has been reset! Please check email!")
                .status(200)
                .errors(false).build();
    }

    public UUID sendToken(HttpServletRequest req, String email) {
        UUID token = UUID.randomUUID();
        emailServiceImpl.confirmResetPassword(req, email, token);
        return token;
    }

}
