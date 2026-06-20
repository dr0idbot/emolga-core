package io.droidbot.emolga.base.ui;

public enum EmolgaThemes {

	COZY_PAPER("cozy-paper"), FRESH_MINT("fresh-mint"), MONOCHROME("monochrome"), NIGHT_OPERATOR("night-operator"),
	SUNSET_GLASS("sunset-glass");

	private String stylesheet;

	EmolgaThemes(String stylesheet) {
		this.stylesheet = stylesheet;
	}

	public String getStylesheet() {
		return "themes/" + stylesheet + ".css";
	}

}
