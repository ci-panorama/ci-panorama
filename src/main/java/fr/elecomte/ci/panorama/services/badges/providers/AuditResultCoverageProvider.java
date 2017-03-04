package fr.elecomte.ci.panorama.services.badges.providers;

import fr.elecomte.ci.panorama.services.badges.BadgeValue;
import fr.elecomte.ci.panorama.services.badges.BadgeValue.BadgeColor;
import fr.elecomte.ci.panorama.services.payloads.extracts.AuditResultPayloadExtract;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class AuditResultCoverageProvider extends BadgeValueProvider<AuditResultPayloadExtract> {

	/**
	 * 
	 */
	public AuditResultCoverageProvider() {
		super();
	}

	@Override
	public boolean isLogo() {
		return false;
	}

	/**
	 * @param entity
	 * @return
	 * @see fr.elecomte.ci.panorama.services.badges.providers.BadgeValueProvider#getValue(java.lang.Object)
	 */
	@Override
	public BadgeValue getValue(AuditResultPayloadExtract extract) {

		// Avoid missing extract
		if (extract == null) {
			return new BadgeValue("pending", BadgeColor.GREY);
		}

		// Must handle partial / missing payload counts

		// No count
		if (extract.getCoverage() == 0) {
			return new BadgeValue("?", BadgeColor.ORANGE);
		}

		// Get coverage
		return new BadgeValue(extract.getCoverage() + "%", BadgeColor.GREEN);
	}

}
