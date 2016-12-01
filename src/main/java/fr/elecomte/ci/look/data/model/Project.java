package fr.elecomte.ci.look.data.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import fr.elecomte.ci.look.data.model.Project.ProjectGroup;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Entity
@NamedNativeQueries({
		// Project groups
		@NamedNativeQuery(
				name = "Project.findProjectGroups",
				query = "select "
						+ "pg.code_name as CODENAME, "
						+ "pd.common_name as COMMONNAME, "
						+ "pd.version as MAXVERSION, "
						+ "pg.knew_versions_count as KNEWVERSIONSCOUNT "
						+ "from ("
						+ "	select "
						+ "		code_name, "
						+ "		max(semver_hash) as semver_hash, "
						+ "		count(id) as knew_versions_count "
						+ "	from Project group by code_name"
						+ ") pg "
						+ "inner join Project pd on pd.code_name = pg.code_name and pd.semver_hash = pg.semver_hash",
				resultClass = ProjectGroup.class,
				resultSetMapping = "ProjectGroup"),

		// "Fresh" version of project (last with an updated result)
		@NamedNativeQuery(
				name = "Project.findFreshProject",
				query = "select p.* from Project p inner join Result r on r.project_id = p.id where p.code_name = ?1 order by r.result_time desc limit 1",
				resultClass = Project.class),

		// "Fresh" version of project for specified result type
		@NamedNativeQuery(
				name = "Project.findFreshProjectForResultType",
				query = "select p.* from Project p inner join Result r on r.project_id = p.id where p.code_name = ?1 and r.type like ?2 order by r.result_time desc limit 1",
				resultClass = Project.class),

		// "Fresh" version of project (last with an updated result)
		@NamedNativeQuery(
				name = "Project.findFreshProjectWithoutResultType",
				query = "select p.* from Project p inner join Result r on r.project_id = p.id where p.code_name = ?1 and r.type not like ?2 order by r.result_time desc limit 1",
				resultClass = Project.class)
})
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "ProjectGroup", classes = {
				@ConstructorResult(targetClass = ProjectGroup.class,
						columns = {
								@ColumnResult(name = "CODENAME", type = String.class),
								@ColumnResult(name = "COMMONNAME", type = String.class),
								@ColumnResult(name = "MAXVERSION", type = String.class),
								@ColumnResult(name = "KNEWVERSIONSCOUNT", type = Integer.class)
						})
		})
})
@Table(indexes = { @Index(columnList = "codeName"), @Index(columnList = "semverHash") })
public class Project extends LiveCiEntity {

	private String codeName;

	private String commonName;

	private LocalDate inceptionDate;

	private String description;

	private String version;

	private Long semverHash;

	private String language;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Collection<Result> results = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Repository> repositories;

	@ManyToOne(optional = true, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Team team;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	private Tool productionTool;

	/**
	 * 
	 */
	public Project() {
		super();
	}

	/**
	 * @return the semverHash
	 */
	public Long getSemverHash() {
		return this.semverHash;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return this.language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @param semverHash
	 *            the semverHash to set
	 */
	public void setSemverHash(Long semverHash) {
		this.semverHash = semverHash;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCommonName() {
		return this.commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * @return the inceptionDate
	 */
	public LocalDate getInceptionDate() {
		return this.inceptionDate;
	}

	/**
	 * @param inceptionDate
	 *            the inceptionDate to set
	 */
	public void setInceptionDate(LocalDate inceptionDate) {
		this.inceptionDate = inceptionDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Collection<Result> getResults() {
		return this.results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
	}

	public Collection<Repository> getRepositories() {
		return this.repositories;
	}

	public void setRepositories(Collection<Repository> repositories) {
		this.repositories = repositories;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Tool getProductionTool() {
		return this.productionTool;
	}

	public void setProductionTool(Tool productionTool) {
		this.productionTool = productionTool;
	}

	public static class ProjectGroup {

		private final String code;

		private final String name;

		private final String lastVersion;

		private final Integer knewVersionsCount;

		public ProjectGroup(String code, String name, String lastVersion, Integer knewVersionsCount) {
			super();
			this.code = code;
			this.name = name;
			this.lastVersion = lastVersion;
			this.knewVersionsCount = knewVersionsCount;
		}

		/**
		 * @return the code
		 */
		public String getCode() {
			return this.code;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * @return the lastVersion
		 */
		public String getLastVersion() {
			return this.lastVersion;
		}

		/**
		 * @return the knewVersionsCount
		 */
		public Integer getKnewVersionsCount() {
			return this.knewVersionsCount;
		}

	}
}
