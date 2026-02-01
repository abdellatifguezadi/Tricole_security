package org.tricol.supplierchain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPermissionDisplayResponse {
    private Long id;
    private String permissionName;
    private String description;
    private String resource;
    private String action;
    private boolean active;
    private Long grantedBy;
    private LocalDateTime grantedAt;
    private LocalDateTime revokedAt;
}
