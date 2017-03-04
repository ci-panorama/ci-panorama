package fr.elecomte.ci.panorama.services.badges.providers;

import fr.elecomte.ci.panorama.data.model.CiEntity;
import fr.elecomte.ci.panorama.services.badges.BadgeValue;

/**
 * @author elecomte
 * @since 0.1.0
 * @param <T>
 */
@SuppressWarnings("unused")
public abstract class BadgeValueProvider<T> {

	public abstract boolean isLogo();

	public BadgeValue getValue(T entity) {
		return null;
	}

	public String getLogo(T entity) {
		return null;
	}
}