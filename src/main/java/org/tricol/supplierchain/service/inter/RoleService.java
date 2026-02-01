package org.tricol.supplierchain.service.inter;

import org.tricol.supplierchain.dto.response.RoleDisplayResponse;

import java.util.List;

public interface RoleService {
    List<RoleDisplayResponse> getAllRoles();
}

