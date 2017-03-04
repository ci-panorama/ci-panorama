package fr.elecomte.ci.panorama.services.badges.providers;

import fr.elecomte.ci.panorama.data.model.Result;
import fr.elecomte.ci.panorama.services.badges.BadgeValue;
import fr.elecomte.ci.panorama.services.badges.BadgeValue.BadgeColor;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ResultSuccessProvider extends BadgeValueProvider<Result> {

	private static final BadgeValue SUCCESS = new BadgeValue("success", BadgeColor.GREEN);
	private static final BadgeValue PENDING = new BadgeValue("pending", BadgeColor.GREY);
	private static final BadgeValue FAILED = new BadgeValue("failed", BadgeColor.RED);

	@Override
	public boolean isLogo() {
		return false;
	}

	@Override
	public BadgeValue getValue(Result entity) {
		if (entity == null) {
			return PENDING;
		}
		return entity.isSuccess() ? SUCCESS : FAILED;
	}
}