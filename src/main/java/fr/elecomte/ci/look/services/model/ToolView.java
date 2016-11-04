package fr.elecomte.ci.look.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ToolView extends View {

	@JsonInclude(Include.ALWAYS)
	private String name;

	@JsonInclude(Include.NON_EMPTY)
	private String version;

	@JsonInclude(Include.NON_EMPTY)
	private String vendorName;

	/**
	 * 
	 */
	public ToolView() {
		super();
	}

	/**
	 * @param name
	 * @param version
	 */
	public ToolView(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}

	/**
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}
