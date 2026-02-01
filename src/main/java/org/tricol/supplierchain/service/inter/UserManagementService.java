package org.tricol.supplierchain.service.inter;

import org.tricol.supplierchain.dto.request.UserPermissionRequest;
import org.tricol.supplierchain.dto.response.UserPermissionResponse;
import org.tricol.supplierchain.dto.response.UserDisplayResponse;
import org.tricol.supplierchain.dto.response.PermissionDisplayResponse;

import java.util.List;

public interface UserManagementService {
    UserPermissionResponse assignPermissionToUser(UserPermissionRequest request, Long adminId);
    void removePermissionFromUser(Long userId, Long permissionId);
    void activatePermission(Long userId, Long permissionId);
    void deactivatePermission(Long userId, Long permissionId);
    void assignRoleToUser(Long userId, Long roleId);

    // Display methods (non-paginated)
    List<UserDisplayResponse> getAllUsers();
    UserDisplayResponse getUserById(Long userId);
    List<PermissionDisplayResponse> getAllPermissions();
    UserDisplayResponse getUserWithPermissions(Long userId);
    List<UserDisplayResponse> searchUsers(String keyword);
}
