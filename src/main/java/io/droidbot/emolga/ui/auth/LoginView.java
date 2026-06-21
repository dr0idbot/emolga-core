package io.droidbot.emolga.ui.auth;

import org.springframework.security.core.AuthenticationException;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import io.droidbot.emolga.auth.AuthService;
import io.droidbot.emolga.ui.EmolgaConstants;

@Route(value = "login", autoLayout = false)
@AnonymousAllowed
public class LoginView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public LoginView(AuthService authService) {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		addClassName(EmolgaConstants.CSS_LOGIN_PAGE);

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

		var loginFormImage = new Image(EmolgaConstants.APP_LOGO_URL, EmolgaConstants.APP_NAME);
		loginFormImage.addClassName(EmolgaConstants.CSS_LOGIN_FORM_IMAGE);

		HorizontalLayout layout = new HorizontalLayout(loginForm, loginFormImage);

		layout.addClassName(EmolgaConstants.CSS_LOGIN_LAYOUT);

		add(layout);
	}
}