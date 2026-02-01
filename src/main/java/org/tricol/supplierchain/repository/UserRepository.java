package org.tricol.supplierchain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tricol.supplierchain.entity.UserApp;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Long> {
    @Query("SELECT u FROM UserApp u " +
           "LEFT JOIN FETCH u.role r " +
           "LEFT JOIN FETCH r.permissions " +
           "LEFT JOIN FETCH u.userPermissions up " +
           "LEFT JOIN FETCH up.permission " +
           "WHERE u.username = :username")
    Optional<UserApp> findByUsername(@Param("username") String username);

    Optional<UserApp> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // New methods for display functionality
    @Query("SELECT u FROM UserApp u " +
           "LEFT JOIN FETCH u.role r " +
           "LEFT JOIN FETCH u.userPermissions up " +
           "LEFT JOIN FETCH up.permission " +
           "WHERE u.id = :id")
    Optional<UserApp> findByIdWithPermissions(@Param("id") Long id);

    Page<UserApp> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String email, Pageable pageable);

    // Non-paginated search variant (no fetch)
    List<UserApp> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String email);

    // Fetch-join variants to avoid lazy loading outside transaction
    @Query("SELECT DISTINCT u FROM UserApp u " +
           "LEFT JOIN FETCH u.role r " +
           "LEFT JOIN FETCH r.permissions rp " +
           "LEFT JOIN FETCH u.userPermissions up " +
           "LEFT JOIN FETCH up.permission p")
    List<UserApp> findAllWithPermissions();

    @Query("SELECT DISTINCT u FROM UserApp u " +
           "LEFT JOIN FETCH u.role r " +
           "LEFT JOIN FETCH r.permissions rp " +
           "LEFT JOIN FETCH u.userPermissions up " +
           "LEFT JOIN FETCH up.permission p " +
           "WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UserApp> searchByUsernameOrEmailWithPermissions(@Param("keyword") String keyword);
}
