package com.poly.petfoster.service.admin.role;

import com.poly.petfoster.request.role.UpdateRoleRequest;
import com.poly.petfoster.response.ApiResponse;

public interface RoleService {
    
    public ApiResponse updateRole(UpdateRoleRequest updateRoleRequest);

}
