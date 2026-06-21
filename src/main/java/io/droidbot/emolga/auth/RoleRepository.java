package io.droidbot.emolga.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<EmolgaRole, Long> {
    Optional<EmolgaRole> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}
