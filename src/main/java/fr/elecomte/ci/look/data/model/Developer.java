package fr.elecomte.ci.look.data.model;

import javax.persistence.Entity;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
public class Developer extends LiveCiEntity {

	private String fullname;

	private String trigram;

	private String email;

	private String companyName;

	private String imageUrl;

	/**
	 * @return
	 */
	public String getFullname() {
		return this.fullname;
	}

	/**
	 * @param fullname
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * @param imageUrl
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

}
