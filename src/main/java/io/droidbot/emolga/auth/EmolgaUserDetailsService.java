package io.droidbot.emolga.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmolgaUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(EmolgaUserDetailsService.class);

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public EmolgaUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found: {}", username);
            return new UsernameNotFoundException("User not found: " + username);
        });

        log.debug("Loaded user details for: {}", username);

        List<SimpleGrantedAuthority> authorities = userRoleRepository.findByUserId(user.getId())
                .stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRole().getRoleName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                user.isEnabled(),
                true,
                true,
                !user.isAccountLocked(),
                authorities);
    }
}
