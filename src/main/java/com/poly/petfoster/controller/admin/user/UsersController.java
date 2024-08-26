package com.poly.petfoster.controller.admin.user;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.users.CreateaUserManageRequest;
import com.poly.petfoster.request.users.UpdateUserRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.user.UserService;

@RestController
@RequestMapping("/api/admin/users")
public class UsersController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUser(@RequestHeader("Authorization") String jwt,
            @RequestParam("keyword") Optional<String> keyword,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> pages,
            @RequestParam("role") Optional<String> roles) {
        return ResponseEntity.ok(userService.getAllUser(jwt, keyword, sort, roles, pages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/chart-info/{id}")
    public ResponseEntity<ApiResponse> getChartInfoUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getChart(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> getUserWithUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserWithUsername(username));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createUser(
            @ModelAttribute("user") CreateaUserManageRequest createaUserManageRequest) {
        return ResponseEntity.ok(userService.createUser(createaUserManageRequest));
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse> updateUser(@ModelAttribute("user") UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
