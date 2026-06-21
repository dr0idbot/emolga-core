package io.droidbot.emolga.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<EmolgaUserRole, Long> {
    List<EmolgaUserRole> findByUserId(Long userId);
    List<EmolgaUserRole> findByRoleId(Long roleId);
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}
