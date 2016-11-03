package com.elecomte.ci.look.services.model;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class JsonPayload {

	private final String value;

	public JsonPayload(String value) {
		super();
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	}

}
