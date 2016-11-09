package fr.elecomte.ci.look.services.model;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class TeamView extends View {

	@JsonInclude(Include.NON_EMPTY)
	private String name;

	@JsonInclude(Include.ALWAYS)
	private List<DeveloperView> developers;

	public TeamView() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the developers
	 */
	public Collection<DeveloperView> getDevelopers() {
		return this.developers;
	}

	/**
	 * @param developers
	 *            the developers to set
	 */
	public void setDevelopers(List<DeveloperView> developers) {
		this.developers = developers;
	}

}
