package com.poly.petfoster.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.service.EmailService;
import com.poly.petfoster.ultils.MailUtils;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    MailUtils mailUtils;
    @Autowired
    UserRepository userRepository;

    @Override
    public void sendVerificationEmail(HttpServletRequest req, String email, UUID token) {
        String verificationLink = Constant.BASE_URL + "verify?code=" + token;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("action_url", verificationLink);
            map.put("name", req.getAttribute("username").toString());
            mailUtils.sendTemplateEmail(email, "Active your account!", "active", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmResetPassword(HttpServletRequest req, String email, UUID token) {
        String verificationLink = Constant.CLIENT_BASE_URL + "reset-password?code=" + token;
        User u = userRepository.findByEmail(email).orElse(null);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("action_url", verificationLink);
            map.put("name", u.getDisplayName());
            map.put("browser_name", req.getHeader("USER-AGENT"));
            mailUtils.sendTemplateEmail(email, "Confirm reset your password!", "reset", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
