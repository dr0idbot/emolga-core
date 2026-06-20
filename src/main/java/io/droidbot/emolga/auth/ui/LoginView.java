package io.droidbot.emolga.auth.ui;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import io.droidbot.emolga.auth.AuthConstants;
import io.droidbot.emolga.auth.AuthService;
import io.droidbot.emolga.auth.CustomUserDetailsService;
import io.droidbot.emolga.auth.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Route(value = AuthConstants.ROUTE_LOGIN)
@PageTitle(AuthConstants.LOGIN_TITLE)
public class LoginView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    private final LoginForm loginForm;

    LoginView(AuthService authService, JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.loginForm = new LoginForm();

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.addLoginListener(event -> {
            try {
                var response = VaadinServletResponse.getCurrent();
                var token = authService.authenticate(event.getUsername(), event.getPassword(), response);

                var userDetails = userDetailsService.loadUserByUsername(event.getUsername());
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                var request = VaadinServletRequest.getCurrent();
                if (request != null) {
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request.getHttpServletRequest()));
                }
                SecurityContextHolder.getContext().setAuthentication(authToken);

                log.info("Login successful for user: {}", event.getUsername());
                getUI().ifPresent(ui -> ui.navigate(""));
            } catch (Exception e) {
                log.warn("Login failed for user: {}", event.getUsername());
                loginForm.setError(true);
            }
        });

        add(loginForm);
    }
}
