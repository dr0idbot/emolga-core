package io.droidbot.emolga.ui.auth;

import org.springframework.security.core.AuthenticationException;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import io.droidbot.emolga.auth.AuthService;

@Route(value = "login", autoLayout = false)
@AnonymousAllowed
public class LoginView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public LoginView(AuthService authService) {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		setAlignSelf(Alignment.CENTER);

		LoginForm loginForm = new LoginForm();
		loginForm.setForgotPasswordButtonVisible(false);

		loginForm.addLoginListener(event -> {

			try {
				authService.login(event.getUsername(), event.getPassword());
				UI.getCurrent().navigate("");

			} catch (AuthenticationException e) {
				loginForm.setError(true);
			}

		});

		add(loginForm);
	}
}