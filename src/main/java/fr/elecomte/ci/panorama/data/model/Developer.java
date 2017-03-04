package fr.elecomte.ci.panorama.data.model;

import javax.persistence.Entity;
import javax.persistence.Lob;

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

	@Lob
	private byte[] imageUrl;

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
	public byte[] getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * @param imageUrl
	 */
	public void setImageUrl(byte[] imageUrl) {
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

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Developer other = (Developer) obj;
		if (this.email == null) {
			if (other.email != null)
				return false;
		} else if (!this.email.equals(other.email))
			return false;
		return true;
	}

}
