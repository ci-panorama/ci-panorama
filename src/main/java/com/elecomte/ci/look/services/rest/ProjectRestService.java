package com.elecomte.ci.look.services.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elecomte.ci.look.services.model.ProjectGroupView;
import com.elecomte.ci.look.services.model.ProjectRecord;
import com.elecomte.ci.look.services.model.ProjectView;
import com.elecomte.ci.look.services.processes.ProjectInformationProcess;

/**
 * @author elecomte
 * @since 0.1.0
 */
@RestController
@RequestMapping(Constants.API_ROOT + "/projects")
public class ProjectRestService {

	@Autowired
	private ProjectInformationProcess projects;

	/**
	 * @return
	 */
	@RequestMapping(value = "/groups", method = GET)
	@ResponseBody
	public List<ProjectGroupView> groups() {

		return this.projects.getAllExistingProjectGroups();
	}

	/**
	 * @param projectCodeName
	 * @return
	 */
	@RequestMapping(value = "/{projectCodeName}", method = GET)
	@ResponseBody
	public List<ProjectView> projects(@PathVariable String projectCodeName) {

		return this.projects.getAllProjectsVersions(projectCodeName);
	}

	/**
	 * @param record
	 */
	@RequestMapping(method = PUT)
	@ResponseBody
	public void record(@RequestBody @Valid ProjectRecord record) {

		this.projects.recordProjectInformation(record);
	}
}
