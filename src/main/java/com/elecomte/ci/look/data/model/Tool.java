package com.elecomte.ci.look.data.model;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.elecomte.ci.look.data.model.Tool.ToolGroup;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
@NamedNativeQueries({
		@NamedNativeQuery(
				name = "Tool.findToolGroups",
				query = "select "
						+ "td.type as TYPE, "
						+ "count(td.id) as KNEWINSTANCESCOUNT "
						+ "from Tool td group by type",
				resultClass = ToolGroup.class,
				resultSetMapping = "ToolGroup") })
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "ToolGroup", classes = {
				@ConstructorResult(targetClass = ToolGroup.class,
						columns = {
								@ColumnResult(name = "TYPE", type = String.class),
								@ColumnResult(name = "KNEWINSTANCESCOUNT", type = Integer.class)
						})
		})
})
@Table(indexes = { @Index(columnList = "name"), @Index(columnList = "type") })
public class Tool extends LiveCiEntity {

	private String name;

	private String vendorName;

	private String version;

	private Long semverHash;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ToolType type;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return this.vendorName;
	}

	/**
	 * @param vendorName
	 *            the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public ToolType getType() {
		return this.type;
	}

	public void setType(ToolType type) {
		this.type = type;
	}

	/**
	 * @return the semverHash
	 */
	public Long getSemverHash() {
		return this.semverHash;
	}

	/**
	 * @param semverHash
	 *            the semverHash to set
	 */
	public void setSemverHash(Long semverHash) {
		this.semverHash = semverHash;
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class ToolGroup {

		private final ToolType type;

		private final Integer knewInstancesCount;

		/**
		 * @param type
		 * @param knewInstancesCount
		 */
		public ToolGroup(String type, Integer knewInstancesCount) {
			super();
			this.type = type != null ? ToolType.valueOf(type) : null;
			this.knewInstancesCount = knewInstancesCount;
		}

		/**
		 * @return the type
		 */
		public ToolType getType() {
			return this.type;
		}

		/**
		 * @return the knewInstancesCount
		 */
		public Integer getKnewInstancesCount() {
			return this.knewInstancesCount;
		}

	}
}
