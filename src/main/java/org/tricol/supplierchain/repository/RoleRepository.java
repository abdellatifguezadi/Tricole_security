package org.tricol.supplierchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tricol.supplierchain.entity.RoleApp;
import org.tricol.supplierchain.enums.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleApp, Long> {
    Optional<RoleApp> findByName(RoleName name);

    @Query("SELECT r.name , count(p.id) FROM RoleApp r join Permission p group by r.name")
    List<Object[]> findRoleWithPermissionCount();
}
