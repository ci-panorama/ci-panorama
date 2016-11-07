package fr.elecomte.ci.look.services.badges.providers;

import fr.elecomte.ci.look.services.badges.BadgeValue;
import fr.elecomte.ci.look.services.badges.BadgeValue.BadgeColor;
import fr.elecomte.ci.look.services.payloads.extracts.TestResultPayloadExtract;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class TestResultCountProvider extends BadgeValueProvider<TestResultPayloadExtract> {

	/**
	 * 
	 */
	public TestResultCountProvider() {
		super();
	}

	@Override
	public boolean isLogo() {
		return false;
	}

	/**
	 * @param entity
	 * @return
	 * @see fr.elecomte.ci.look.services.badges.providers.BadgeValueProvider#getValue(java.lang.Object)
	 */
	@Override
	public BadgeValue getValue(TestResultPayloadExtract extract) {

		// Must handle partial / missing payload counts

		// Success cases
		if (extract.getAssociatedResult().isSuccess()) {

			// No success count
			if (extract.getSuccess() == 0) {
				// All ignored
				if (extract.getIgnored() > 0) {
					return new BadgeValue("ignored", BadgeColor.ORANGE);
				}

				// Missing payload, simpler result
				return new BadgeValue("success", BadgeColor.GREEN);
			}

			// Successful with count
			return new BadgeValue(extract.getSuccess() + "/" + (extract.getFailed() + extract.getSuccess()), BadgeColor.GREEN);
		}

		// No count on failed, simpler result
		if (extract.getFailed() == 0) {
			return new BadgeValue("failed", BadgeColor.RED);
		}

		// Real fealure
		return new BadgeValue(extract.getSuccess() + "/" + (extract.getFailed() + extract.getSuccess()), BadgeColor.RED);
	}

}
