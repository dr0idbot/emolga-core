package io.droidbot.emolga.ui;

public enum EmolgaTheme {
	DEFAULT(null), COZY_PAPER("themes/cozy-paper.css"), FRESH_MINT("themes/fresh-mint.css"),
	MONOCHROME("themes/monochrome.css"), NIGHT_OPERATOR("themes/night-operator.css"),
	SUNSET_GLASS("themes/sunset-glass.css");

	private final String styleSheet;

	EmolgaTheme(String styleSheet) {
		this.styleSheet = styleSheet;
	}

	public String getStyleSheet() {
		return styleSheet;
	}
}