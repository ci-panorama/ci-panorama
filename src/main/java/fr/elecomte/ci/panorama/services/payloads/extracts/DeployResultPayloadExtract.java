package fr.elecomte.ci.panorama.services.payloads.extracts;

import java.util.List;

/**
 * <p>
 * Payload for a Deploy. Model is :
 * 
 * <pre>
 * "payload": {
 *      "path": "{{location of deployed project files. Can be groupId/version path for maven projects}}",
 *      "repository": "{{repository name - should be a project Repository name for automatic aggregation}}",
 *      "files": ["{{an array of deployed filenames. Can be maven classifier for example}}"]
 * }
 * </pre>
 * </p>
 * 
 * @author elecomte
 * @since 0.1.0
 */
public class DeployResultPayloadExtract extends ResultPayloadExtract {

	private String path;

	private String repository;

	private List<String> files;

	/**
	 * 
	 */
	public DeployResultPayloadExtract() {
		super();
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the repository
	 */
	public String getRepository() {
		return this.repository;
	}

	/**
	 * @param repository
	 *            the repository to set
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}

	/**
	 * @return the files
	 */
	public List<String> getFiles() {
		return this.files;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public void setFiles(List<String> files) {
		this.files = files;
	}

}
