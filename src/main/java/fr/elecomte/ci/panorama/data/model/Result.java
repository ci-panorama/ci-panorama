package fr.elecomte.ci.panorama.data.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
public class Result extends CiEntity {

	private boolean success;

	private String message;

	@Enumerated(EnumType.STRING)
	private ResultType type;

	private String payload;

	private LocalDateTime resultTime;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Project project;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Tool tool;

	/**
	 * 
	 */
	public Result() {
		super();
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Tool getTool() {
		return this.tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	public String getPayload() {
		return this.payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return this.project;
	}

	/**
	 * @return the resultTime
	 */
	public LocalDateTime getResultTime() {
		return this.resultTime;
	}

	/**
	 * @param resultTime
	 *            the resultTime to set
	 */
	public void setResultTime(LocalDateTime resultTime) {
		this.resultTime = resultTime;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
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
