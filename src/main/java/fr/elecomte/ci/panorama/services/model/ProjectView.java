package fr.elecomte.ci.panorama.services.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ProjectView extends View {

	@JsonInclude(Include.NON_EMPTY)
	@NotNull
	private String code;

	@JsonInclude(Include.ALWAYS)
	@NotNull
	private String version;

	@JsonInclude(Include.NON_EMPTY)
	private String name;

	@JsonInclude(Include.NON_EMPTY)
	private LocalDate inception;

	@JsonInclude(Include.NON_EMPTY)
	private String description;

	@JsonInclude(Include.NON_EMPTY)
	@Size(max = 10)
	private String language;

	@JsonInclude(Include.NON_EMPTY)
	private Collection<RepositoryView> repositories;

	@JsonInclude(Include.NON_EMPTY)
	private TeamView team;

	private LocalDateTime created;

	private LocalDateTime updated;

	/**
	 * 
	 */
	public ProjectView() {
		super();
	}

	/**
	 * @param code
	 * @param version
	 */
	public ProjectView(String code, String version) {
		super();
		this.code = code;
		this.version = version;
	}

	/**
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getInception() {
		return this.inception;
	}

	public void setInception(LocalDate inception) {
		this.inception = inception;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<RepositoryView> getRepositories() {
		return this.repositories;
	}

	public void setRepositories(Collection<RepositoryView> repositories) {
		this.repositories = repositories;
	}

	public TeamView getTeam() {
		return this.team;
	}

	public void setTeam(TeamView team) {
		this.team = team;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return this.language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the created
	 */
	public LocalDateTime getCreated() {
		return this.created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	/**
	 * @return the updated
	 */
	public LocalDateTime getUpdated() {
		return this.updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

}
