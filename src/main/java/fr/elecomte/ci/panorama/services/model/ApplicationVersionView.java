package fr.elecomte.ci.panorama.services.model;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ApplicationVersionView {

	private final String version;

	private final String codename;

	private final String build;

	/**
	 * @param version
	 * @param codename
	 * @param build
	 */
	public ApplicationVersionView(String version, String codename, String build) {
		super();
		this.version = version;
		this.codename = codename;
		this.build = build;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @return the codename
	 */
	public String getCodename() {
		return this.codename;
	}

	/**
	 * @return the build
	 */
	public String getBuild() {
		return this.build;
	}

}
