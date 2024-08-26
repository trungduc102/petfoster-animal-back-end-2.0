package com.poly.petfoster.service.impl.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.entity.Authorities;
import com.poly.petfoster.entity.Role;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.AuthoritiesRepository;
import com.poly.petfoster.repository.RoleRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.role.UpdateRoleRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.role.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Override
    public ApiResponse updateRole(UpdateRoleRequest updateRoleRequest) {

        User user = userRepository.findById(updateRoleRequest.getUserId()).orElse(null);
        if (user == null) {
            return ApiResponse.builder()
                .message("User not found!")
                .status(HttpStatus.NOT_FOUND.value())
                .errors(true)
                .build();
        }

        Role role = roleRepository.findById(updateRoleRequest.getRoleId()).orElse(null);
        if (role == null) {
            return ApiResponse.builder()
                .message("Role not found!")
                .status(HttpStatus.NOT_FOUND.value())
                .errors(true)
                .build();
        }

        List<Authorities> authorities = authoritiesRepository.findByUser(user);
        if (authorities.isEmpty()) {
            return ApiResponse.builder()
                .message("No data available")
                .status(HttpStatus.NOT_FOUND.value())
                .errors(true)
                .build();
        }

        authorities.get(0).setRole(role);
        authoritiesRepository.save(authorities.get(0));

        user.setAuthorities(authorities);
        userRepository.save(user);

        return ApiResponse.builder().message("Successfully").status(200).errors(false).build();
    }
    
}
