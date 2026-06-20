package io.droidbot.emolga.base.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinServletRequest;
import io.droidbot.emolga.auth.AuthConstants;
import io.droidbot.emolga.auth.CustomUserDetailsService;
import io.droidbot.emolga.auth.JwtService;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.Arrays;

public abstract class EmolgaViewBase extends VerticalLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(EmolgaViewBase.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal())) {
            log.debug("User already authenticated: {}", auth.getName());
            return;
        }

        var request = VaadinServletRequest.getCurrent();
        if (request != null) {
            var cookies = request.getCookies();
            if (cookies != null) {
                var jwtCookie = Arrays.stream(cookies)
                        .filter(c -> AuthConstants.JWT_COOKIE_NAME.equals(c.getName()))
                        .findFirst();

                if (jwtCookie.isPresent()) {
                    var token = jwtCookie.get().getValue();
                    if (jwtService.isTokenValid(token)) {
                        var username = jwtService.extractUsername(token);
                        var userDetails = userDetailsService.loadUserByUsername(username);

                        var authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request.getHttpServletRequest()));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        log.debug("Authenticated user via cookie in view check: {}", username);
                        return;
                    }
                }
            }
        }

        log.debug("No valid authentication found, redirecting to login");
        event.forwardTo(AuthConstants.ROUTE_LOGIN);
    }
}
