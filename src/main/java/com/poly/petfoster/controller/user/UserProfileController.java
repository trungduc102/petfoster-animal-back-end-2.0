package com.poly.petfoster.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.poly.petfoster.request.ProfileRepuest;
import com.poly.petfoster.request.users.ChangePasswordRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.user.ProfileService;
import com.poly.petfoster.service.user.UserService;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    ProfileService profileService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(profileService.getProfile(jwt));
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@ModelAttribute("user") ProfileRepuest profileRepuest,
            @RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(profileService.updateProfile(profileRepuest, jwt));
    }

    @PostMapping("/profile/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            @RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(profileService.changePassword(changePasswordRequest, jwt));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ApiResponse> getProductFileWithUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithUsername(username));
    }

}
