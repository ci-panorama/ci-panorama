package fr.elecomte.ci.look.services.model;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class RepositoryView extends View {

	private String name;

	private String type;

	private String url;

	public RepositoryView() {
		super();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
