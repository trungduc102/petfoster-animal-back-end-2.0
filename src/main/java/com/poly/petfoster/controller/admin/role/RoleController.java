package com.poly.petfoster.controller.admin.role;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.role.UpdateRoleRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.role.RoleService;

@RestController
@RequestMapping("/api/admin/authorities")
public class RoleController {

    @Autowired
    RoleService roleService;
    
    @PutMapping("")
    public ResponseEntity<ApiResponse> updateRole(@Valid @RequestBody UpdateRoleRequest updateRoleRequest) {
        return ResponseEntity.ok(roleService.updateRole(updateRoleRequest));
    }

}
