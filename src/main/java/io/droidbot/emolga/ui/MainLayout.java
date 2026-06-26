package io.droidbot.emolga.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;

import io.droidbot.emolga.ui.wordle.WordleView;
import io.github.dr0idbot.vlucide.LucideIcon;

@Layout
public class MainLayout extends AppLayout {

	private static final long serialVersionUID = 1L;

	private final Button themeToggle;
	private ColorScheme.Value currentScheme;

	public MainLayout() {
		setPrimarySection(Section.DRAWER);

		addToNavbar(new DrawerToggle(), createTitle());

		themeToggle = new Button();
		themeToggle.addClassName("theme-toggle");

		var drawerContent = new VerticalLayout();
		drawerContent.addClassName("drawer-content");

		var header = createDrawerHeader();
		var scroller = new Scroller(createSideNav());

		drawerContent.add(header, scroller, themeToggle);
		drawerContent.setFlexGrow(1, scroller);
		drawerContent.setPadding(false);
		drawerContent.setSpacing(false);

		addToDrawer(drawerContent);

		initTheme();
	}

	private void initTheme() {
		var page = UI.getCurrent().getPage();
		page.executeJs("try { " + "  var t = localStorage.getItem('emolga-theme'); "
				+ "  if (t === 'DARK') { document.querySelector('html').style.colorScheme = 'dark'; } "
				+ "  else if (t === 'LIGHT') { document.querySelector('html').style.colorScheme = 'light'; } "
				+ "  else { document.querySelector('html').style.colorScheme = 'light dark'; } "
				+ "  return t || 'LIGHT_DARK'; " + "} catch(e) { return 'LIGHT_DARK'; }").then(String.class, stored -> {
					currentScheme = switch (stored) {
					case "DARK" -> ColorScheme.Value.DARK;
					case "LIGHT" -> ColorScheme.Value.LIGHT;
					default -> ColorScheme.Value.LIGHT_DARK;
					};
					updateToggleState();
				});

		themeToggle.addClickListener(e -> cycleScheme());
	}

	private void cycleScheme() {
		currentScheme = switch (currentScheme) {
		case DARK -> ColorScheme.Value.LIGHT;
		case LIGHT -> ColorScheme.Value.LIGHT_DARK;
		default -> ColorScheme.Value.DARK;
		};
		applyScheme();
	}

	private void applyScheme() {
		var page = UI.getCurrent().getPage();
		page.setColorScheme(currentScheme);
		page.executeJs("try { localStorage.setItem('emolga-theme', $0); } catch(e) {}", currentScheme.name());
		updateToggleState();
	}

	private void updateToggleState() {
		var icon = switch (currentScheme) {
		case DARK -> LucideIcon.MOON.create();
		case LIGHT -> LucideIcon.SUN.create();
		default -> LucideIcon.MONITOR.create();
		};
		var label = switch (currentScheme) {
		case DARK -> "Dark";
		case LIGHT -> "Light";
		default -> "System";
		};
		themeToggle.setIcon(icon);
		themeToggle.setText(label);
	}

	private Span createTitle() {
		var title = new Span("Emolga Core");
		return title;
	}

	private Component createDrawerHeader() {
		var logo = new Image("images/emolga.png", "Emolga");
		logo.addClassName("drawer-header__logo");
		logo.setWidth(36, com.vaadin.flow.component.Unit.PIXELS);
		logo.setHeight(36, com.vaadin.flow.component.Unit.PIXELS);

		var name = new Span("Emolga Core");
		name.addClassName("drawer-header__name");

		var header = new HorizontalLayout(logo, name);
		header.addClassName("drawer-header");
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		return header;
	}

	private SideNav createSideNav() {
		var nav = new SideNav();

		var home = new SideNavItem("Home", MainView.class, LucideIcon.HOME.create());
		home.setMatchNested(true);

		var about = new SideNavItem("About", AboutView.class, LucideIcon.INFO.create());
		about.setMatchNested(true);

		var wordle = new SideNavItem("Wordle", WordleView.class, LucideIcon.GAMEPAD_2.create());
		wordle.setMatchNested(true);

		nav.addItem(home, about, wordle);
		return nav;
	}

}
