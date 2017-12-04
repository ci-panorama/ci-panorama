/*
 * Copyright 2016 Emmanuel Lecomte
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *  
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package fr.elecomte.ci.panorama.services.caches;

/**
 * <p>
 * Basic holder model for badge data caching. Badge data is internally managed as a String
 * representation of a SVG content. All pictures are included as inlined B64 encoded src.
 * So caching just has to manage String values.
 * </p>
 * <p>
 * The BadgesCache must manage concurrency.
 * </p>
 * 
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
