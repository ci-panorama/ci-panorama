package fr.elecomte.ci.look.services.payloads.extracts;

/**
 * <p>
 * Payload for an Install. Model is :
 * 
 * <pre>
 * "payload": {
 *     "environment": "{{Freely specified environment name where the install was processed}}",
 *     "user": "{{Freely specified person name who launched the install - should be a developer email for automatic aggregation}}"
 * }
 * </pre>
 * </p>
 * 
 * @author elecomte
 * @since 0.1.0
 */
public class InstallResultPayloadExtract extends ResultPayloadExtract {

	private String environment;

	private String user;

	/**
	 * 
	 */
	public InstallResultPayloadExtract() {
		super();
	}

	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return this.environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
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

}
