package io.droidbot.emolga.base.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.shared.Registration;

public class ThemeChanger extends Select<EmolgaThemes> {

	private static final long serialVersionUID = 1L;

	public ThemeChanger() {
		setLabel("Select Theme");
		setItems(EmolgaThemes.values());
		setValue(EmolgaThemes.COZY_PAPER);

		addValueChangeListener(
				event -> switchTheme(event.getSource().getUI().orElse(UI.getCurrent()), event.getValue()));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		UI ui = attachEvent.getUI();
		Registration registration = ComponentUtil.getData(ui, Registration.class);
		if (registration != null) {
			switchTheme(ui, EmolgaThemes.COZY_PAPER);
		}
	}

	/**
	 * Switch the current theme style sheet for a new one.
	 *
	 * @param ui    the ui in use, not {@code null}
	 * @param theme new style to replace current with
	 */
	protected void switchTheme(UI ui, EmolgaThemes theme) {
		Registration registration = ComponentUtil.getData(ui, Registration.class);
		if (registration != null) {
			registration.remove();
		}

		registration = ui.getPage().addStyleSheet(theme.getStylesheet());

		ComponentUtil.setData(ui, Registration.class, registration);
	}
}