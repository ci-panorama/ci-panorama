package com.elecomte.ci.look.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ProjectGroupView extends View {

	@JsonInclude(Include.ALWAYS)
	private String name;

	@JsonInclude(Include.ALWAYS)
	private ProjectView last;

	@JsonInclude(Include.ALWAYS)
	private int knewVersionsCount;

	/**
	 * @return the last
	 */
	public ProjectView getLast() {
		return this.last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(ProjectView last) {
		this.last = last;
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
	 * @return the knewVersionsCount
	 */
	public int getKnewVersionsCount() {
		return this.knewVersionsCount;
	}

	/**
	 * @param knewVersionsCount
	 *            the knewVersionsCount to set
	 */
	public void setKnewVersionsCount(int knewVersionsCount) {
		this.knewVersionsCount = knewVersionsCount;
	}
}
