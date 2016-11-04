package fr.elecomte.ci.look.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ToolGroupView extends View {

	@JsonInclude(Include.ALWAYS)
	private String type;

	@JsonInclude(Include.ALWAYS)
	private int knewInstancesCount;

	/**
	 * @return the knewInstancesCount
	 */
	public int getKnewInstancesCount() {
		return this.knewInstancesCount;
	}

	/**
	 * @param knewInstancesCount the knewInstancesCount to set
	 */
	public void setKnewInstancesCount(int knewInstancesCount) {
		this.knewInstancesCount = knewInstancesCount;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
