package fr.elecomte.ci.look.services.badges.providers;

import fr.elecomte.ci.look.services.badges.BadgeValue;
import fr.elecomte.ci.look.services.payloads.PayloadExtract;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class TestResultCountProvider extends BadgeValueProvider<PayloadExtract> {

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
	public BadgeValue getValue(PayloadExtract entity) {
		return super.getValue(entity);
	}

}
