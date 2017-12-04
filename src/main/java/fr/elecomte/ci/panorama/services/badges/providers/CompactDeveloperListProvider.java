package fr.elecomte.ci.panorama.services.badges.providers;

/**
 * @author elecomte
 * @since v0.1
 * @version 1
 */
public class CompactDeveloperListProvider extends DeveloperListProvider {

	/**
	 * @param gridImgLineSize
	 * @param maxCount
	 */
	public CompactDeveloperListProvider() {
		super(5, 10);
	}

}
