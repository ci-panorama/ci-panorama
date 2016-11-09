package fr.elecomte.ci.look.services.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class DeveloperView extends View {

	@JsonInclude(Include.ALWAYS)
	@NotNull
	private String email;

	@JsonInclude(Include.NON_EMPTY)
	private String fullname;

	@JsonInclude(Include.NON_EMPTY)
	private String companyName;

	@JsonInclude(Include.NON_EMPTY)
	private String imageUrl;

	@JsonInclude(Include.NON_EMPTY)
	private String trigram;

	public DeveloperView() {
		super();
	}

	/**
	 * @return the trigram
	 */
	public String getTrigram() {
		return this.trigram;
	}

	/**
	 * @param trigram
	 *            the trigram to set
	 */
	public void setTrigram(String trigram) {
		this.trigram = trigram;
	}

	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return this.fullname;
	}

	/**
	 * @param fullname
	 *            the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
