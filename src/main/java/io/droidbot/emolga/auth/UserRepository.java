package io.droidbot.emolga.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<EmolgaUser, Long> {
    Optional<EmolgaUser> findByUsername(String username);
    Optional<EmolgaUser> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
