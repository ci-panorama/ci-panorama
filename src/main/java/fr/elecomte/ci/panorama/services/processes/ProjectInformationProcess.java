package fr.elecomte.ci.panorama.services.processes;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.elecomte.ci.panorama.data.model.Developer;
import fr.elecomte.ci.panorama.data.model.Project;
import fr.elecomte.ci.panorama.data.model.ResultType;
import fr.elecomte.ci.panorama.data.model.Team;
import fr.elecomte.ci.panorama.data.model.Tool;
import fr.elecomte.ci.panorama.data.model.Project.ProjectGroup;
import fr.elecomte.ci.panorama.data.repositories.DeveloperRepository;
import fr.elecomte.ci.panorama.data.repositories.ProjectRepository;
import fr.elecomte.ci.panorama.data.repositories.TeamRepository;
import fr.elecomte.ci.panorama.services.caches.BadgesCache;
import fr.elecomte.ci.panorama.services.model.DeveloperView;
import fr.elecomte.ci.panorama.services.model.ProjectGroupView;
import fr.elecomte.ci.panorama.services.model.ProjectRecord;
import fr.elecomte.ci.panorama.services.model.ProjectView;
import fr.elecomte.ci.panorama.services.model.TeamView;

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
	private TeamRepository teams;

	@Autowired
	private DeveloperRepository developers;

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

		// A team was specified and must be updated
		if (toSave.getTeam() != null) {
			toSave.setTeam(refreshTeam(toSave.getTeam()));
		}

		// Save or create
		toSave = refreshProject(toSave);

		// Update all references (caches)
		markUpdatedProject(toSave);
	}

	/**
	 * @param team
	 * @return
	 */
	Team refreshTeam(Team team) {

		// Update each specified developers if required
		List<Developer> updatedDevelopers = team.getDevelopers().stream().map(this.developers::mergeWithExistingAndSave)
				.collect(Collectors.toList());

		// Link will be updated from Team update
		team.setDevelopers(updatedDevelopers);

		// Then the team, which may be identified from the developers
		return this.teams.mergeWithExistingAndSave(team);
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
	 * @return number of distinct projects
	 */
	int getProjectsCount() {
		return this.projects.countDifferentProjects();
	}

	/**
	 * @param group
	 * @return
	 */
	private static ProjectGroupView viewFromProjectGroup(ProjectGroup group) {

		ProjectGroupView view = new ProjectGroupView();

		view.setLast(new ProjectView(group.getName(), group.getLastVersion()));
		view.setCode(group.getCode());
		view.setName(group.getName());
		view.setKnewVersionsCount(group.getKnewVersionsCount().intValue());
		view.setKnewDevelopersCount(group.getKnewDevelopersCount().intValue());

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
		view.setLanguage(project.getLanguage());
		view.setCreated(project.getInitTime());
		view.setUpdated(project.getLastUpdateTime());

		if (project.getTeam() != null) {
			view.setTeam(viewFromTeam(project.getTeam()));
		}

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
		project.setLanguage(view.getLanguage());

		if (view.getTeam() != null) {
			project.setTeam(teamFromView(view.getTeam()));
		}

		return project;
	}

	/**
	 * @param view
	 * @return
	 */
	static Developer developerFromView(DeveloperView view) {

		Developer dev = new Developer();
		dev.setCompanyName(view.getCompanyName());
		dev.setEmail(view.getEmail());
		dev.setFullname(view.getFullname());
		dev.setImageUrl(view.getImageUrl() != null ? view.getImageUrl().getBytes() : null);

		// Generate trigram if possible
		dev.setTrigram(
				view.getTrigram() == null && view.getEmail() != null
						? generateTrigramFromEmail(view.getEmail())
						: view.getTrigram());

		return dev;
	}

	/**
	 * @param dev
	 * @return
	 */
	static DeveloperView viewFromDeveloper(Developer dev) {

		DeveloperView view = new DeveloperView();

		view.setCompanyName(dev.getCompanyName());
		view.setEmail(dev.getEmail());
		view.setFullname(dev.getFullname());
		if (dev.getImageUrl() != null) {
			view.setImageUrl(new String(dev.getImageUrl()));
		}
		view.setTrigram(dev.getTrigram());

		return view;
	}

	/**
	 * @param view
	 * @return
	 */
	static Team teamFromView(TeamView view) {

		Team team = new Team();

		team.setName(view.getName());
		team.setDevelopers(view.getDevelopers().stream().map(ProjectInformationProcess::developerFromView).collect(Collectors.toSet()));

		return team;
	}

	/**
	 * @param view
	 * @return
	 */
	static TeamView viewFromTeam(Team team) {

		TeamView view = new TeamView();

		view.setName(team.getName());
		view.setDevelopers(team.getDevelopers().stream().map(ProjectInformationProcess::viewFromDeveloper).collect(Collectors.toList()));

		return view;
	}

	/**
	 * @param email
	 * @return
	 */
	static String generateTrigramFromEmail(String email) {
		String alias = email.substring(0, email.indexOf('@'));

		int dot = alias.indexOf('.');
		int len = alias.length();

		if (dot > 0 && len > 3) {
			return alias.substring(0, 1) + alias.substring(dot + 1, dot + 3);
		}

		return alias.substring(0, len > 3 ? 3 : len - 1);
	}
}
