package io.droidbot.emolga.ui;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.ScrollerVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

import io.droidbot.emolga.auth.AuthConstants;
import io.droidbot.emolga.auth.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;

@PermitAll
@Layout
public final class EmolgaLayout extends AppLayout {

	private static final long serialVersionUID = 1L;

	private final Avatar avatar;
	private final Span displayLabel;
	private final UserRepository userRepository;

	EmolgaLayout(UserRepository userRepository) {
		this.userRepository = userRepository;

		setPrimarySection(Section.DRAWER);

		avatar = new Avatar();
		avatar.addThemeVariants(AvatarVariant.AURA_FILLED, AvatarVariant.XSMALL);

		displayLabel = new Span();

		var footer = createFooter();

		var header = createApplicationHeader();
		var scroller = new Scroller(createSideNav());
		scroller.addThemeVariants(ScrollerVariant.OVERFLOW_INDICATORS);

		var drawerContent = new VerticalLayout(header, scroller, footer);
		drawerContent.setHeightFull();
		drawerContent.setFlexGrow(1, scroller);

		addToDrawer(drawerContent);
	}

	private Component createFooter() {

		var userInfo = new HorizontalLayout(avatar, displayLabel);
		userInfo.setAlignItems(FlexComponent.Alignment.CENTER);
		userInfo.setSpacing(true);

		var themeChanger = new ThemeChanger();
		themeChanger.setWidthFull();

		var colorSchemeSelect = new Select<ColorScheme.Value>();
		colorSchemeSelect.setLabel("Color Scheme");

		colorSchemeSelect.setItems(ColorScheme.Value.LIGHT, ColorScheme.Value.DARK, ColorScheme.Value.DARK_LIGHT);

		colorSchemeSelect.setItemLabelGenerator(value -> switch (value) {
		case LIGHT -> "Light";
		case DARK -> "Dark";
		case DARK_LIGHT -> "System";
		default -> value.name();
		});

		colorSchemeSelect.setValue(ColorScheme.Value.DARK_LIGHT);

		colorSchemeSelect.addValueChangeListener(event -> {
			if (event.getValue() != null) {
				UI.getCurrentOrThrow().getPage().setColorScheme(event.getValue());
			}
		});

		var logoutBtn = new Button(EmolgaConstants.LOGOUT, event -> logout());
		logoutBtn.addThemeVariants(ButtonVariant.TERTIARY);
		logoutBtn.setWidthFull();

		MenuBar menuBar = new MenuBar();
		menuBar.addThemeVariants(MenuBarVariant.AURA_FILLED);

		MenuItem user = menuBar.addItem(userInfo);
		SubMenu userSubMenu = user.getSubMenu();

		// Existing theme changer
		userSubMenu.addComponent(themeChanger);

		// Color scheme selector
		userSubMenu.addComponent(new Hr());
		userSubMenu.addComponent(colorSchemeSelect);

		// Logout section
		userSubMenu.addComponent(new Hr());
		userSubMenu.addComponent(logoutBtn);

		var footer = new VerticalLayout(menuBar);
		footer.setPadding(true);
		footer.setSpacing(true);
		footer.addClassName(EmolgaConstants.CSS_APP_FOOTER);

		return footer;
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		updateUserInfo();
	}

	private void updateUserInfo() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !EmolgaConstants.ANONYMOUS_USER.equals(auth.getPrincipal())) {

			var username = auth.getName();
			var user = userRepository.findByUsername(username);

			if (user.isPresent()) {
				var emolgaUser = user.get();
				var displayName = emolgaUser.getDisplayName();

				if (displayName == null || displayName.isBlank()) {
					displayName = username;
				}

				avatar.setName(displayName);
				displayLabel.setText(displayName);
			} else {
				avatar.setName(username);
				displayLabel.setText(username);
			}
		}
	}

	private Component createApplicationHeader() {
		var appLogo = new Avatar(EmolgaConstants.APP_NAME, "lucide/ratio.svg");
		appLogo.addClassName(EmolgaConstants.CSS_APP_LOGO);
		appLogo.addThemeVariants(AvatarVariant.AURA_FILLED, AvatarVariant.XSMALL);

		var appName = new Span(EmolgaConstants.APP_NAME);
		appName.addClassName(EmolgaConstants.CSS_APP_NAME);

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
			Component icon;

			if (menuEntry.icon().contains(".svg")) {
				icon = new SvgIcon(menuEntry.icon());
			} else {
				icon = new Icon(menuEntry.icon());
			}

			return new SideNavItem(menuEntry.title(), menuEntry.menuClass(), icon);
		}

		return new SideNavItem(menuEntry.title(), menuEntry.menuClass());
	}
}