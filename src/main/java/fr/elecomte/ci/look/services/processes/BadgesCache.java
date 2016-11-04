package fr.elecomte.ci.look.services.processes;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Component
public class BadgesCache {

	private ConcurrentHashMap<BadgeProjectId, BadgeRepository> projectBadgeRepositories = new ConcurrentHashMap<>();

	public BadgesCache() {

	}

	/**
	 * @param projectCode
	 * @param projectVersion
	 */
	void dropCache(String projectCode, String projectVersion) {
		this.projectBadgeRepositories.remove(new BadgeProjectId(projectCode, projectVersion));
	}

	/**
	 * @param projectCode
	 * @param projectVersion
	 * @param badgeIdentifier
	 * @return
	 */
	String getCachedBadge(String projectCode, String projectVersion, String badgeIdentifier) {

		BadgeProjectId id = new BadgeProjectId(projectCode, projectVersion);

		BadgeRepository repo = this.projectBadgeRepositories.get(id);

		if (repo == null) {
			return null;
		}

		return repo.getBadge(badgeIdentifier);
	}

	/**
	 * @param projectCode
	 * @param projectVersion
	 * @param badgeIdentifier
	 * @param badge
	 */
	void putCachedBadge(String projectCode, String projectVersion, String badgeIdentifier, String badge) {

		BadgeProjectId id = new BadgeProjectId(projectCode, projectVersion);

		BadgeRepository repo = this.projectBadgeRepositories.get(id);

		if (repo == null) {
			repo = new BadgeRepository();
			this.projectBadgeRepositories.put(id, repo);
		}

		repo.keepBadge(badgeIdentifier, badge);
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	private static class BadgeRepository {

		private ConcurrentHashMap<String, String> badges = new ConcurrentHashMap<>();

		BadgeRepository() {
			super();
		}

		/**
		 * @param badgeIdentifier
		 * @return
		 */
		String getBadge(String badgeIdentifier) {
			return this.badges.get(badgeIdentifier);
		}

		/**
		 * @param badgeIdentifier
		 * @param badge
		 */
		void keepBadge(String badgeIdentifier, String badge) {
			this.badges.put(badgeIdentifier, badge);
		}
	}

	/**
	 * Cache id
	 * 
	 * @author elecomte
	 * @since 0.1.0
	 */
	private static class BadgeProjectId {

		private final int hash;

		BadgeProjectId(String projectCode, String projectVersion) {
			this.hash = projectCode.hashCode() + 37 * projectVersion.hashCode();
		}

		/**
		 * @return
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return this.hash;
		}

	}

}
