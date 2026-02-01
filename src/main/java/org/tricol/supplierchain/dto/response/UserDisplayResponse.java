package org.tricol.supplierchain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDisplayResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private boolean enabled;
    private boolean locked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private RoleDisplayResponse role;
    private List<UserPermissionDisplayResponse> permissions;
}
