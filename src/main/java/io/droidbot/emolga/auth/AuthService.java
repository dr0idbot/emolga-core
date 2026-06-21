package io.droidbot.emolga.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.VaadinServletResponse;

import jakarta.servlet.http.Cookie;

@Service
public class AuthService {

	private final AuthenticationManager authManager;
	private final JwtService jwtService;

	public AuthService(AuthenticationManager authManager, JwtService jwtService) {
		this.authManager = authManager;
		this.jwtService = jwtService;
	}

	public void login(String username, String password) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		UserDetails user = (UserDetails) auth.getPrincipal();
		String jwt = jwtService.generateToken(user);

		Cookie cookie = new Cookie("access_token", jwt);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(86400);

		VaadinServletResponse.getCurrent().addCookie(cookie);
	}

}