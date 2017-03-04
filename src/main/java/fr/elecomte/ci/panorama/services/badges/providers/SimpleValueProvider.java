package fr.elecomte.ci.panorama.services.badges.providers;

import fr.elecomte.ci.panorama.services.badges.BadgeValue;
import fr.elecomte.ci.panorama.services.badges.BadgeValue.BadgeColor;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class SimpleValueProvider extends BadgeValueProvider<String> {

	private final static BadgeColor VALUE_COLOR = BadgeColor.DARK_GREY;

	@Override
	public boolean isLogo() {
		return false;
	}

	@Override
	public BadgeValue getValue(String value) {
		return new BadgeValue(value, VALUE_COLOR);
	}
}