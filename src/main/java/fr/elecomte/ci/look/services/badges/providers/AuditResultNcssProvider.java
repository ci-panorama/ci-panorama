package fr.elecomte.ci.look.services.badges.providers;

import fr.elecomte.ci.look.services.badges.BadgeValue;
import fr.elecomte.ci.look.services.badges.BadgeValue.BadgeColor;
import fr.elecomte.ci.look.services.payloads.extracts.AuditResultPayloadExtract;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class AuditResultNcssProvider extends BadgeValueProvider<AuditResultPayloadExtract> {

	/**
	 * 
	 */
	public AuditResultNcssProvider() {
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
	public BadgeValue getValue(AuditResultPayloadExtract extract) {

		// Avoid missing extract
		if (extract == null) {
			return new BadgeValue("pending", BadgeColor.GREY);
		}

		// Must handle partial / missing payload counts

		// No count
		if (extract.getNcss() == 0) {
			return new BadgeValue("?", BadgeColor.ORANGE);
		}

		// Get NCSS value
		return new BadgeValue(String.valueOf(extract.getNcss()), BadgeColor.GREEN);
	}

}
