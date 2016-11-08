package fr.elecomte.ci.look.services.badges;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class BadgeValue {

	private final String title;
	private final BadgeColor color;

	/**
	 * @param title
	 * @param color
	 */
	public BadgeValue(String title, BadgeColor color) {
		super();
		this.title = title;
		this.color = color;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @return the color
	 */
	public BadgeColor getColor() {
		return this.color;
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public enum BadgeColor {

		BLACK("#000"),
		GREEN("#4c1"),
		LIGHT_GREY("#EEE"),
		GREY("grey"),
		DARK_GREY("#444"),
		RED("red"),
		ORANGE("orange"),
		NONE("#fff");

		private final String color;

		private BadgeColor(String color) {
			this.color = color;
		}

		/**
		 * @return the color
		 */
		public String getColor() {
			return this.color;
		}
	}
}