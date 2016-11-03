package com.elecomte.ci.look.services.processes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elecomte.ci.look.data.model.Project;
import com.elecomte.ci.look.data.model.Project.ProjectGroup;
import com.elecomte.ci.look.data.model.Tool;
import com.elecomte.ci.look.data.repositories.ProjectRepository;
import com.elecomte.ci.look.services.model.ProjectGroupView;
import com.elecomte.ci.look.services.model.ProjectRecord;
import com.elecomte.ci.look.services.model.ProjectView;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Service
public class ProjectInformationProcess extends AbstractRecordProcess {

	@Autowired
	private ProjectRepository projects;

	/**
	 * @param record
	 */
	public void recordProjectInformation(ProjectRecord record) {

		Project toSave = projectFromView(record.getPayload());

		Tool tool = getToolForRecord(record);

		toSave.setProductionTool(tool);

		// Hash Semver
		toSave.setSemverHash(this.semverHashGenerator.hashVersion(toSave.getVersion()));

		this.projects.mergeWithExistingAndSave(toSave);
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
