package io.droidbot.emolga.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RolePermissionRepository extends JpaRepository<EmolgaRolePermission, Long> {
    List<EmolgaRolePermission> findByRoleId(Long roleId);
    List<EmolgaRolePermission> findByPermissionKey(String permissionKey);
    boolean existsByRoleIdAndPermissionKey(Long roleId, String permissionKey);
}
