package io.droidbot.emolga.auth;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.equals("/login")
				|| path.startsWith("/VAADIN/")
				|| path.startsWith("/themes/")
				|| path.startsWith("/lucide/")
				|| path.startsWith("/aura/")
				|| path.endsWith(".css")
				|| path.endsWith(".js")
				|| path.endsWith(".svg");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String token = null;

		if (request.getCookies() != null) {

			for (Cookie cookie : request.getCookies()) {
				log.trace("Found cookie: name={}", cookie.getName());

				if (AuthConstants.JWT_COOKIE_NAME.equals(cookie.getName())) {
					token = cookie.getValue();
					log.trace("Found JWT cookie");
				}
			}
		}

		if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			log.debug("Attempting JWT authentication for {}", request.getRequestURI());
			String username = jwtService.extractUsername(token);
			log.debug("JWT username: {}", username);

			UserDetails user = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(auth);
			log.debug("JWT authentication set for user: {}", username);
		} else {
			log.trace("No JWT token found or already authenticated");
		}

		chain.doFilter(request, response);
	}
}