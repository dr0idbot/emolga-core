package io.droidbot.emolga.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.shared.Registration;

public class ThemeChanger extends Select<EmolgaTheme> {

	private static final long serialVersionUID = 1L;

	public ThemeChanger() {
		setLabel("Select Theme");
		setItems(EmolgaTheme.values());
		setValue(EmolgaTheme.COZY_PAPER);
		setItemLabelGenerator(theme -> theme.name().replace('_', ' ').toLowerCase());

		addValueChangeListener(
				event -> switchTheme(event.getSource().getUI().orElse(UI.getCurrent()), event.getValue()));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		UI ui = attachEvent.getUI();
		Registration registration = ComponentUtil.getData(ui, Registration.class);

		if (registration == null) {
			switchTheme(ui, EmolgaTheme.COZY_PAPER);
		}
	}

	protected void switchTheme(UI ui, EmolgaTheme theme) {
		Registration registration = ComponentUtil.getData(ui, Registration.class);

		if (registration != null) {
			registration.remove();
		}

		registration = null;

		if (theme.getStyleSheet() != null) {
			registration = ui.getPage().addStyleSheet(theme.getStyleSheet());
		}

		ComponentUtil.setData(ui, Registration.class, registration);
	}
}