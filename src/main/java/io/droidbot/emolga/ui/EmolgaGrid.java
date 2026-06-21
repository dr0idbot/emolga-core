package io.droidbot.emolga.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import io.github.dr0idbot.vlucide.LucideIcon;

@StyleSheet("/emolga/emolga-grid.css")
public class EmolgaGrid<T> extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final Grid<T> grid;

	private final HorizontalLayout header = new HorizontalLayout();
	private final HorizontalLayout headerContent = new HorizontalLayout();
	private final HorizontalLayout headerActions = new HorizontalLayout();

	private final HorizontalLayout footer = new HorizontalLayout();
	private final HorizontalLayout footerContent = new HorizontalLayout();

	private final H3 titleLabel;
	private final Span rowCount = new Span(EmolgaConstants.ROWS_LABEL + "0");

	private Button refreshButton;
	private Button downloadButton;

	public EmolgaGrid(Class<T> beanType, String title) {

		grid = new Grid<>(beanType, false);
		grid.addThemeVariants(GridVariant.ROW_STRIPES, GridVariant.COLUMN_BORDERS);

		titleLabel = new H3(title);
		titleLabel.addClassName(EmolgaConstants.CSS_GRID_TITLE);

		header.addClassName(EmolgaConstants.CSS_GRID_HEADER);
		header.setWidthFull();
		header.setPadding(true);
		header.setAlignItems(FlexComponent.Alignment.CENTER);

		headerContent.addClassName(EmolgaConstants.CSS_GRID_HEADER_CONTENT);
		headerContent.setSpacing(true);
		headerContent.setAlignItems(FlexComponent.Alignment.CENTER);

		headerActions.addClassName(EmolgaConstants.CSS_GRID_HEADER_ACTIONS);
		headerActions.setSpacing(true);
		headerActions.setAlignItems(FlexComponent.Alignment.CENTER);

		footer.addClassName(EmolgaConstants.CSS_GRID_FOOTER);
		footer.setWidthFull();
		footer.setPadding(true);
		footer.setAlignItems(FlexComponent.Alignment.CENTER);

		footerContent.addClassName(EmolgaConstants.CSS_GRID_FOOTER_CONTENT);
		footerContent.setSpacing(true);
		footerContent.setAlignItems(FlexComponent.Alignment.CENTER);

		rowCount.addClassName(EmolgaConstants.CSS_GRID_ROW_COUNT);

		header.add(titleLabel, headerContent, headerActions);
		header.expand(headerContent);

		footer.add(footerContent, rowCount);
		footer.expand(footerContent);

		add(header, grid, footer);

		refreshButton = new Button(LucideIcon.REFRESH_CW.create());
		refreshButton.setTooltipText(EmolgaConstants.REFRESH);
		refreshButton.setVisible(false);
		refreshButton.setEnabled(false);
		headerActions.add(refreshButton);

		downloadButton = new Button(LucideIcon.DOWNLOAD.create());
		downloadButton.setTooltipText(EmolgaConstants.DOWNLOAD);
		headerActions.add(downloadButton);

		setPadding(false);
		setSpacing(false);

		grid.setSizeFull();
		setSizeFull();
		addClassName(EmolgaConstants.CSS_EMOLGA_GRID);
	}

	public Grid<T> getGrid() {
		return grid;
	}

	public void addToHeader(Component... components) {
		headerContent.add(components);
	}

	public void addToFooter(Component... components) {
		footerContent.add(components);
	}

	public Button enableRefresh() {
		refreshButton.setVisible(true);
		refreshButton.setEnabled(true);
		return refreshButton;
	}

	public void setRowCount(int count) {
		rowCount.setText(EmolgaConstants.ROWS_LABEL + count);
	}
}
