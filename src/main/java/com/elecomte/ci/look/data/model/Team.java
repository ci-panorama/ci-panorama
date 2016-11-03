package com.elecomte.ci.look.data.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
public class Team extends LiveCiEntity {

	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<Developer> developers;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Developer> getDevelopers() {
		return this.developers;
	}

	public void setDevelopers(Collection<Developer> developers) {
		this.developers = developers;
	}

}
