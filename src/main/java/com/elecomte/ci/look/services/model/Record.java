package com.elecomte.ci.look.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 * @param <T>
 */
public abstract class Record<T extends View> {

	@JsonInclude(Include.ALWAYS)
	private ToolView source;

	@JsonInclude(Include.NON_NULL)
	private T payload;

	public ToolView getSource() {
		return this.source;
	}

	public void setSource(ToolView source) {
		this.source = source;
	}

	public T getPayload() {
		return this.payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

}
