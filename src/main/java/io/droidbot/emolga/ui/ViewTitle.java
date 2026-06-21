package io.droidbot.emolga.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ViewTitle extends Composite<HorizontalLayout> {

	private static final long serialVersionUID = 1L;

	public ViewTitle(String title) {
		addClassName("view-title");
		var h = new H1(title);
		getContent().add(new DrawerToggle(), h);
		getContent().setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
	}
}
