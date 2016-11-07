package fr.elecomte.ci.look.services.processes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Component
public class BadgesCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(BadgesCache.class);

	private Map<BadgeProjectId, BadgeRepository> projectBadgeRepositories = new HashMap<>();

	public BadgesCache() {

	}

	/**
	 * @param projectCode
	 * @param projectVersion
	 */
	void dropCache(String projectCode, String projectVersion) {

		LOGGER.debug("Drop from cache everything for project {}/{}", projectCode, projectVersion);

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
			LOGGER.debug("No cache entry found yet for project {}/{}", projectCode, projectVersion);
			return null;
		}

		String value = repo.getBadge(badgeIdentifier);

		if (LOGGER.isDebugEnabled()) {
			if (value != null) {
				LOGGER.debug("Found a cache entry for project {}/{}, badge {}, of {} caracters", projectCode, projectVersion,
						badgeIdentifier, Integer.valueOf(value.length()));
			} else {
				LOGGER.debug("No cache entry found for project {}/{}, badge {}", projectCode, projectVersion, badgeIdentifier);
			}
		}

		return value;
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
			LOGGER.debug("No cache entry found yet for project {}/{}, create it", projectCode, projectVersion);
			repo = new BadgeRepository();
			this.projectBadgeRepositories.put(id, repo);
		}

		repo.keepBadge(badgeIdentifier, badge);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Added a cache entry for project {}/{}, badge {}, of {} caracters. Now cache size is {} entries with"
					+ " a total of {} caracters", projectCode, projectVersion, badgeIdentifier, Integer.valueOf(badge.length()),
					totalSize(), totalEntriesLengthSum());
		}
	}

	/**
	 * @return
	 */
	private Integer totalSize() {
		return this.projectBadgeRepositories.values().stream().collect(Collectors.summingInt(BadgeRepository::size));
	}

	/**
	 * @return
	 */
	private Integer totalEntriesLengthSum() {
		return this.projectBadgeRepositories.values().stream().collect(Collectors.summingInt(BadgeRepository::getEntriesLengthSum));
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	private static class BadgeRepository {

		private Map<String, String> badges = new ConcurrentHashMap<>();

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

		/**
		 * @return
		 */
		Integer getEntriesLengthSum() {
			return this.badges.entrySet().stream().collect(Collectors.summingInt(e -> e.getKey().length() + e.getValue().length()));
		}

		/**
		 * @return
		 */
		int size() {
			return this.badges.size();
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

		/**
		 * @param projectCode
		 * @param projectVersion
		 */
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

		/**
		 * @param obj
		 * @return
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BadgeProjectId other = (BadgeProjectId) obj;
			if (this.hash != other.hash)
				return false;
			return true;
		}

	}

}
