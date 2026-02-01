package org.tricol.supplierchain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tricol.supplierchain.dto.request.RegisterRequest;
import org.tricol.supplierchain.dto.response.AuthResponse;
import org.tricol.supplierchain.dto.response.UserDisplayResponse;
import org.tricol.supplierchain.dto.response.RoleDisplayResponse;
import org.tricol.supplierchain.dto.response.UserPermissionDisplayResponse;
import org.tricol.supplierchain.dto.response.PermissionDisplayResponse;
import org.tricol.supplierchain.entity.UserApp;
import org.tricol.supplierchain.entity.RoleApp;
import org.tricol.supplierchain.entity.UserPermission;
import org.tricol.supplierchain.entity.Permission;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "userPermissions", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "locked", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserApp toEntity(RegisterRequest request);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "role", expression = "java(user.getRole() != null ? user.getRole().getName().name() : null)")
    @Mapping(target = "authorities", ignore = true)
    AuthResponse toAuthResponse(UserApp user);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "permissions", source = "userPermissions")
    UserDisplayResponse toDisplayResponse(UserApp user);

    RoleDisplayResponse toRoleDisplayResponse(RoleApp role);

    @Mapping(target = "id", expression = "java(userPermission.getPermission() != null ? userPermission.getPermission().getId() : null)")
    @Mapping(target = "permissionName", source = "permission.name")
    @Mapping(target = "description", source = "permission.description")
    @Mapping(target = "resource", source = "permission.resource")
    @Mapping(target = "action", source = "permission.action")
    UserPermissionDisplayResponse toUserPermissionDisplayResponse(UserPermission userPermission);

    PermissionDisplayResponse toPermissionDisplayResponse(Permission permission);
}
