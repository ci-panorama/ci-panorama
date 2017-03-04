package fr.elecomte.ci.panorama.services.badges.providers;

import fr.elecomte.ci.panorama.data.model.ToolType;
import fr.elecomte.ci.panorama.services.badges.Resources;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ToolLogoProvider extends BadgeValueProvider<ToolType> {

	@Override
	public boolean isLogo() {
		return true;
	}

	/**
	 * @param entity
	 * @return
	 * @see fr.elecomte.ci.panorama.services.badges.providers.BadgeValueProvider#getLogo(java.lang.Object)
	 */
	@Override
	public String getLogo(ToolType entity) {
		return Resources.LOGO_ROOT + "/" + entity.name().toLowerCase().replaceFirst("_", "/").replaceAll("_", "-") + Resources.FILE_EXT;
	}

}
