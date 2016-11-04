package fr.elecomte.ci.look.data.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
public class Repository extends LiveCiEntity {

	private String name;

	private String url;

	@Enumerated(EnumType.STRING)
	private RepositoryType type;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public RepositoryType getType() {
		return this.type;
	}

	public void setType(RepositoryType type) {
		this.type = type;
	}

}
