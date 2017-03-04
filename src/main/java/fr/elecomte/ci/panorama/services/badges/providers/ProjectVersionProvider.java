package fr.elecomte.ci.panorama.services.badges.providers;

import com.github.zafarkhaja.semver.Version;

import fr.elecomte.ci.panorama.data.model.Project;
import fr.elecomte.ci.panorama.services.badges.BadgeValue;
import fr.elecomte.ci.panorama.services.badges.BadgeValue.BadgeColor;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ProjectVersionProvider extends BadgeValueProvider<Project> {

	private static final BadgeValue NONE = new BadgeValue("N/A", BadgeColor.RED);

	@Override
	public boolean isLogo() {
		return false;
	}

	@Override
	public BadgeValue getValue(Project entity) {
		if (entity == null) {
			return NONE;
		}

		Version v = Version.valueOf(entity.getVersion());

		int major = v.getMajorVersion();
		int minor = v.getMinorVersion();
		int patch = v.getPatchVersion();

		String value = major + "." + minor + "." + patch;

		if (v.getPreReleaseVersion() != null) {
			return new BadgeValue(value, BadgeColor.DARK_GREY);
		}

		return new BadgeValue(value, BadgeColor.ORANGE);
	}
}