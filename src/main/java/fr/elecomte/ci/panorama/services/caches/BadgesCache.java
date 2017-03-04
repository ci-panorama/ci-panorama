package fr.elecomte.ci.panorama.services.caches;

/**
 * @author elecomte
 * @since 0.1.0
 */
public interface BadgesCache {

	BadgesCache DISABLED = new BadgesCache() {

		@Override
		public Integer totalSize() {
			return Integer.valueOf(0);
		}

		@Override
		public Integer totalEntriesLengthSum() {
			return Integer.valueOf(0);
		}

		@Override
		public void putCachedBadge(String projectCode, String projectVersion, String badgeIdentifier, String badge) {
			// Do nothing
		}

		@Override
		public String getCachedBadge(String projectCode, String projectVersion, String badgeIdentifier) {
			return null;
		}

		@Override
		public void dropCache(String projectCode, String projectVersion) {
			// Do nothing
		}

		@Override
		public void dropAll() {
			// Do nothing
		}
	};

	/**
	 * @param projectCode
	 * @param projectVersion
	 */
	void dropCache(String projectCode, String projectVersion);

	/**
	 * @param projectCode
	 * @param projectVersion
	 * @param badgeIdentifier
	 * @return
	 */
	String getCachedBadge(String projectCode, String projectVersion, String badgeIdentifier);

	/**
	 * @param projectCode
	 * @param projectVersion
	 * @param badgeIdentifier
	 * @param badge
	 */
	void putCachedBadge(String projectCode, String projectVersion, String badgeIdentifier, String badge);

	/**
	 * @return
	 */
	Integer totalSize();

	/**
	 * @return
	 */
	Integer totalEntriesLengthSum();

	/**
	 * 
	 */
	void dropAll();

}
