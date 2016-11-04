package fr.elecomte.ci.look.data.model;

import javax.persistence.Entity;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
public class Developer extends LiveCiEntity {

	private String fullname;

	private String email;

	private String companyName;

	private String imageUrl;

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
