package fr.elecomte.ci.panorama.services.payloads.extracts;

/**
 * <p>
 * Payload for a Deploy. Model is :
 * 
 * <pre>
 * "payload": {
 *      "user": "{{Freely specified person name who launched the build - should be a developer email for automatic aggregation}}",
 *      "duration": {{int nbr of seconds for build execution}}
 * }
 * </pre>
 * </p>
 * 
 * @author elecomte
 * @since 0.1.0
 */
public class BuildResultPayloadExtract extends ResultPayloadExtract {

	private String user;

	private int duration;

	/**
	 * 
	 */
	public BuildResultPayloadExtract() {
		super();
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

}
