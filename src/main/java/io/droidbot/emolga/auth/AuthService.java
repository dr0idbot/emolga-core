package io.droidbot.emolga.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.VaadinServletResponse;

import jakarta.servlet.http.Cookie;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	private final AuthenticationManager authManager;
	private final JwtService jwtService;

	public AuthService(AuthenticationManager authManager, JwtService jwtService) {
		this.authManager = authManager;
		this.jwtService = jwtService;
	}

	public void login(String username, String password) {
		log.debug("Attempting login for user: {}", username);

		try {
			Authentication auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			log.debug("Authentication successful for user: {}", username);

			UserDetails user = (UserDetails) auth.getPrincipal();
			log.debug("User details: username={}, enabled={}, authorities={}", user.getUsername(), user.isEnabled(),
					user.getAuthorities());

			String jwt = jwtService.generateToken(user);
			log.debug("JWT generated for user: {}", username);

			Cookie cookie = new Cookie(AuthConstants.JWT_COOKIE_NAME, jwt);
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			cookie.setPath("/");
			cookie.setMaxAge(86400);

			VaadinServletResponse.getCurrent().addCookie(cookie);
			log.info("Login successful for user: {}", username);
		} catch (BadCredentialsException e) {
			log.warn("Login failed for user: {} - bad credentials", username);
			throw e;
		} catch (AuthenticationException e) {
			log.warn("Login failed for user: {} - {}", username, e.getMessage());
			throw e;
		}
	}

}