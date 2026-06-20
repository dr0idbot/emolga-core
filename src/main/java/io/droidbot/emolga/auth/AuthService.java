package io.droidbot.emolga.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String authenticate(String username, String password, HttpServletResponse response) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Login failed: user '{}' not found", username);
                    return new BadCredentialsException(AuthConstants.LOGIN_FAILED);
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Login failed: incorrect password for user '{}'", username);
            throw new BadCredentialsException(AuthConstants.LOGIN_FAILED);
        }

        var token = jwtService.generateToken(username);
        log.info("User '{}' logged in successfully", username);

        var cookie = new Cookie(AuthConstants.JWT_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

        return token;
    }
}
