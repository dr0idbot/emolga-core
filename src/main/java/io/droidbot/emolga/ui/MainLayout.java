package io.droidbot.emolga.ui;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.ScrollerVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

import io.droidbot.emolga.auth.AuthConstants;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;

@PermitAll
@Layout
public final class MainLayout extends AppLayout {

	private static final long serialVersionUID = 1L;

	private final Avatar avatar;
	private final Span usernameLabel;
	private final VerticalLayout menuPanel;

	MainLayout() {
		setPrimarySection(Section.DRAWER);

		avatar = new Avatar();
		avatar.addThemeVariants(AvatarVariant.AURA_FILLED, AvatarVariant.XSMALL);

		usernameLabel = new Span();

		var userInfo = new HorizontalLayout(avatar, usernameLabel);
		userInfo.setAlignItems(FlexComponent.Alignment.CENTER);
		userInfo.setSpacing(true);
		userInfo.getStyle().set("cursor", "pointer");

		var themeChanger = new ThemeChanger();
		themeChanger.setWidthFull();

		var logoutBtn = new Button("Logout", event -> logout());
		logoutBtn.addThemeVariants(ButtonVariant.TERTIARY);
		logoutBtn.setWidthFull();

		menuPanel = new VerticalLayout(themeChanger, logoutBtn);
		menuPanel.setPadding(true);
		menuPanel.setSpacing(true);
		menuPanel.setVisible(false);

		userInfo.addClickListener(event -> menuPanel.setVisible(!menuPanel.isVisible()));

		var footer = new VerticalLayout(userInfo, menuPanel);
		footer.setPadding(true);
		footer.setSpacing(true);
		footer.addClassName("app-footer");

		var header = createApplicationHeader();
		var scroller = new Scroller(createSideNav());
		scroller.addThemeVariants(ScrollerVariant.OVERFLOW_INDICATORS);

		var drawerContent = new VerticalLayout(header, scroller, footer);
		drawerContent.setHeightFull();
		drawerContent.setFlexGrow(1, scroller);

		addToDrawer(drawerContent);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		updateUserInfo();
	}

	private void updateUserInfo() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
			avatar.setName(auth.getName());
			usernameLabel.setText(auth.getName());
		}
	}

	private Component createApplicationHeader() {
		var appLogo = new Avatar("emolga",
				"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/587.svg");
		appLogo.addClassName("app-logo");
		appLogo.addThemeVariants(AvatarVariant.AURA_FILLED, AvatarVariant.XSMALL);

		var appName = new Span("EMOLGA");
		appName.addClassName("app-name");

		var header = new HorizontalLayout(appLogo, appName);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setPadding(true);
		return header;
	}

	private void logout() {
		var response = VaadinServletResponse.getCurrent();
		if (response != null) {
			var cookie = new Cookie(AuthConstants.JWT_COOKIE_NAME, null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		SecurityContextHolder.clearContext();
		getUI().ifPresent(ui -> ui.getPage().setLocation(AuthConstants.ROUTE_LOGIN));
	}

	private SideNav createSideNav() {
		var nav = new SideNav();
		nav.setMinWidth(200, Unit.PIXELS);
		MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
		return nav;
	}

	private SideNavItem createSideNavItem(MenuEntry menuEntry) {
		if (menuEntry.icon() != null) {
			Component icon = null;
			if (menuEntry.icon().contains(".svg")) {
				icon = new SvgIcon(menuEntry.icon());
			} else {
				icon = new Icon(menuEntry.icon());
			}
			return new SideNavItem(menuEntry.title(), menuEntry.menuClass(), icon);
		} else {
			return new SideNavItem(menuEntry.title(), menuEntry.menuClass());
		}
	}
}
