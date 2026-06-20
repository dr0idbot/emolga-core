package io.droidbot.emolga.base.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
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
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

@Layout
public final class MainLayout extends AppLayout {

	private static final long serialVersionUID = 1L;

	MainLayout() {
		setPrimarySection(Section.DRAWER);
		addToDrawer(createApplicationHeader(), createApplicationDrawer(), createApplicationFooter());
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

	private Component createApplicationDrawer() {
		var scroller = new Scroller(createSideNav());
		scroller.addThemeVariants(ScrollerVariant.OVERFLOW_INDICATORS);
		return scroller;
	}

	private Component createApplicationFooter() {
		var footer = new VerticalLayout(new ThemeChanger(), new Span("Made with ❤️ with Vaadin"));
		footer.setAlignItems(FlexComponent.Alignment.CENTER);
		footer.addClassName("app-footer");
		return footer;
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
