package com.elecomte.ci.look.services.model;

import java.util.List;

import com.elecomte.ci.look.data.model.ResultType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ProjectResultListView extends View {

	@JsonInclude(Include.ALWAYS)
	private ProjectView project;

	@JsonInclude(Include.ALWAYS)
	private List<ResultView> results;

	@JsonInclude(Include.NON_EMPTY)
	private ResultType type;

	public ProjectResultListView() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the project
	 */
	public ProjectView getProject() {
		return this.project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(ProjectView project) {
		this.project = project;
	}

	/**
	 * @return the results
	 */
	public List<ResultView> getResults() {
		return this.results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<ResultView> results) {
		this.results = results;
	}

	/**
	 * @return the type
	 */
	public ResultType getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ResultType type) {
		this.type = type;
	}

}
