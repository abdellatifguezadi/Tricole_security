package org.tricol.supplierchain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tricol.supplierchain.dto.request.UserPermissionRequest;
import org.tricol.supplierchain.dto.response.UserPermissionResponse;
import org.tricol.supplierchain.dto.response.UserDisplayResponse;
import org.tricol.supplierchain.dto.response.PermissionDisplayResponse;
import org.tricol.supplierchain.dto.response.RoleDisplayResponse;
import org.tricol.supplierchain.security.CustomUserDetails;
import org.tricol.supplierchain.service.inter.UserManagementService;
import org.tricol.supplierchain.service.inter.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final RoleService roleService;

    // Display endpoints
    @GetMapping
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<List<UserDisplayResponse>> getAllUsers() {
        List<UserDisplayResponse> users = userManagementService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<UserDisplayResponse> getUserById(@PathVariable Long userId) {
        UserDisplayResponse user = userManagementService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/permissions")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<UserDisplayResponse> getUserWithPermissions(@PathVariable Long userId) {
        UserDisplayResponse user = userManagementService.getUserWithPermissions(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<List<UserDisplayResponse>> searchUsers(@RequestParam String keyword) {
        List<UserDisplayResponse> users = userManagementService.searchUsers(keyword);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/permissions/all")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<List<PermissionDisplayResponse>> getAllPermissions() {
        List<PermissionDisplayResponse> permissions = userManagementService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<List<RoleDisplayResponse>> getAllRoles() {
        List<RoleDisplayResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/permissions")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<UserPermissionResponse> assignPermission(@RequestBody UserPermissionRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserPermissionResponse response = userManagementService.assignPermissionToUser(request, userDetails.getUser().getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<Map<String, String>> removePermission(@PathVariable Long userId, @PathVariable Long permissionId) {
        userManagementService.removePermissionFromUser(userId, permissionId);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Permission removed successfully");
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{userId}/permissions/{permissionId}/activate")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<Map<String, String>> activatePermission(@PathVariable Long userId, @PathVariable Long permissionId) {
        userManagementService.activatePermission(userId, permissionId);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Permission activated successfully");
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{userId}/permissions/{permissionId}/deactivate")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<Map<String, String>> deactivatePermission(@PathVariable Long userId, @PathVariable Long permissionId) {
        userManagementService.deactivatePermission(userId, permissionId);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Permission deactivated successfully");
        return ResponseEntity.ok(body);
    }

    @PostMapping("/{userId}/role/{roleId}")
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    public ResponseEntity<Map<String, String>> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        userManagementService.assignRoleToUser(userId, roleId);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Role assigned successfully");
        return ResponseEntity.ok(body);
    }
}
