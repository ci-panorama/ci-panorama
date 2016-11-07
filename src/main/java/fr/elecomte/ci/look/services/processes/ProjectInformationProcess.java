package fr.elecomte.ci.look.services.processes;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.elecomte.ci.look.data.model.Project;
import fr.elecomte.ci.look.data.model.ResultType;
import fr.elecomte.ci.look.data.model.Tool;
import fr.elecomte.ci.look.data.model.Project.ProjectGroup;
import fr.elecomte.ci.look.data.repositories.ProjectRepository;
import fr.elecomte.ci.look.services.model.ProjectGroupView;
import fr.elecomte.ci.look.services.model.ProjectRecord;
import fr.elecomte.ci.look.services.model.ProjectView;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Service
public class ProjectInformationProcess extends AbstractRecordProcess {

	public static final String PENDING_VERSION = "pending";
	public static final String RELEASED_VERSION = "released";
	public static final String FRESH_VERSION = "fresh";
	public static final String LAST_VERSION = "last";

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInformationProcess.class);

	@Autowired
	private ProjectRepository projects;

	@Autowired
	private BadgesCache badgesCache;

	/**
	 * @param record
	 */
	public void recordProjectInformation(ProjectRecord record) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Processing record of project {}/{} from tool", record.getPayload().getCode(), record.getPayload().getName(),
					record.getSource());
		}

		Project toSave = projectFromView(record.getPayload());
		Tool tool = getToolForRecord(record);
		toSave.setProductionTool(tool);

		// Save or create
		toSave = refreshProject(toSave);

		// Update all references (caches)
		markUpdatedProject(toSave);
	}

	/**
	 * @return
	 */
	public List<ProjectGroupView> getAllExistingProjectGroups() {
		return this.projects.findProjectGroups().stream().map(ProjectInformationProcess::viewFromProjectGroup).collect(Collectors.toList());
	}

	/**
	 * @param projectCode
	 * @return
	 */
	public List<ProjectView> getAllProjectsVersions(String projectCode) {
		return this.projects.findByCodeNameOrderBySemverHashAsc(projectCode).stream().map(ProjectInformationProcess::viewFromProject)
				.collect(Collectors.toList());
	}

	/**
	 * Drop all cache instances on project
	 * 
	 * @param project
	 */
	void markUpdatedProject(Project project) {
		this.badgesCache.dropCache(project.getCodeName(), project.getVersion());
		this.badgesCache.dropCache(project.getCodeName(), RELEASED_VERSION);
		this.badgesCache.dropCache(project.getCodeName(), FRESH_VERSION);
		this.badgesCache.dropCache(project.getCodeName(), PENDING_VERSION);
		this.badgesCache.dropCache(project.getCodeName(), LAST_VERSION);
	}

	/**
	 * @param project
	 * @return
	 */
	Project refreshProject(Project project) {
		// Hash Semver
		project.setSemverHash(this.semverHashGenerator.hashVersion(project.getVersion()));
		return this.projects.mergeWithExistingAndSave(project);
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 */
	Project getProject(String code, String version) {

		switch (version) {
		case LAST_VERSION:
			return this.projects.findFirstByCodeNameOrderBySemverHashDesc(code);
		case FRESH_VERSION:
			return this.projects.findFreshProject(code);
		case PENDING_VERSION:
			return this.projects.findFreshProjectWithoutResultType(code, ResultType.RELEASE.name());
		case RELEASED_VERSION:
			return this.projects.findFreshProjectForResultType(code, ResultType.RELEASE.name());
		default:
			return this.projects.findByCodeNameAndVersion(code, version);
		}
	}

	/**
	 * @param group
	 * @return
	 */
	private static ProjectGroupView viewFromProjectGroup(ProjectGroup group) {

		ProjectGroupView view = new ProjectGroupView();

		view.setLast(new ProjectView(group.getCode(), group.getLastVersion()));
		view.setName(group.getName());
		view.setKnewVersionsCount(group.getKnewVersionsCount().intValue());

		return view;
	}

	/**
	 * @param project
	 * @return
	 */
	static ProjectView viewFromProject(Project project) {

		ProjectView view = new ProjectView();

		view.setCode(project.getCodeName());
		view.setName(project.getCommonName());
		view.setDescription(project.getDescription());
		view.setInception(project.getInceptionDate());
		view.setVersion(project.getVersion());

		return view;
	}

	/**
	 * @param view
	 * @return
	 */
	static Project projectFromView(ProjectView view) {

		Project project = new Project();

		project.setCodeName(view.getCode());
		project.setCommonName(view.getName());
		project.setDescription(view.getDescription());
		project.setInceptionDate(view.getInception());
		project.setVersion(view.getVersion());

		return project;
	}
}
