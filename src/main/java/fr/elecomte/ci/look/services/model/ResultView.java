package fr.elecomte.ci.look.services.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ResultView extends View {

	@JsonInclude(Include.ALWAYS)
	private boolean success;

	@JsonInclude(Include.NON_EMPTY)
	private String message;

	@JsonInclude(Include.NON_EMPTY)
	private JsonPayload payload;

	@JsonInclude(Include.NON_EMPTY)
	private LocalDateTime resultTime;

	/**
	 * 
	 */
	public ResultView() {
		super();
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return this.success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the payload
	 */
	public JsonPayload getPayload() {
		return this.payload;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(JsonPayload payload) {
		this.payload = payload;
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

}
