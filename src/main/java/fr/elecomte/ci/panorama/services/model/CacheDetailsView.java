package fr.elecomte.ci.panorama.services.model;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class CacheDetailsView {

	private final int count;
	private final float sizeKb;

	/**
	 * @param count
	 * @param sizeKb
	 */
	public CacheDetailsView(int count, float sizeKb) {
		super();
		this.count = count;
		this.sizeKb = sizeKb;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @return the sizeKb
	 */
	public float getSizeKb() {
		return this.sizeKb;
	}

}
