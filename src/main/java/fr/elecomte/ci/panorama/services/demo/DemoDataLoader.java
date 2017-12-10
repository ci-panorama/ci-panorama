/*
 * Copyright 2016 Emmanuel Lecomte
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *  
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package fr.elecomte.ci.panorama.services.demo;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.elecomte.ci.panorama.data.model.Developer;
import fr.elecomte.ci.panorama.data.model.Project;
import fr.elecomte.ci.panorama.data.model.Result;
import fr.elecomte.ci.panorama.data.model.ResultType;
import fr.elecomte.ci.panorama.data.model.Team;
import fr.elecomte.ci.panorama.data.model.Tool;
import fr.elecomte.ci.panorama.data.model.ToolType;
import fr.elecomte.ci.panorama.data.repositories.DeveloperRepository;
import fr.elecomte.ci.panorama.data.repositories.ProjectRepository;
import fr.elecomte.ci.panorama.data.repositories.ResultRepository;
import fr.elecomte.ci.panorama.data.repositories.TeamRepository;
import fr.elecomte.ci.panorama.data.repositories.ToolRepository;
import fr.elecomte.ci.panorama.services.badges.ImageUtils;
import fr.elecomte.ci.panorama.services.badges.Resources;
import fr.elecomte.ci.panorama.services.payloads.extracts.AuditResultPayloadExtract;
import fr.elecomte.ci.panorama.services.payloads.extracts.ResultPayloadExtract;
import fr.elecomte.ci.panorama.services.payloads.extracts.TestResultPayloadExtract;
import fr.elecomte.ci.panorama.services.processes.SemverHashGenerator;

/**
 * Data init with demo mode
 * 
 * @author elecomte
 * @since 0.1.0
 */
public class DemoDataLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataLoader.class);

	@Autowired
	private DeveloperRepository developers;

	@Autowired
	private TeamRepository teams;

	@Autowired
	private ProjectRepository projects;

	@Autowired
	private ToolRepository tools;

	@Autowired
	private ResultRepository results;

	@Autowired
	private SemverHashGenerator svhg;

	private ObjectMapper mapper;

	@PostConstruct
	public void launchLoading() throws Exception {
		this.mapper = new ObjectMapper();
		processDataLoading();
	}

	@Transactional
	public void processDataLoading() throws Exception {

		if (this.projects.count() == 0) {

			Tool maven = tool("maven", "3.3.7", ToolType.PRODUCTION_MAVEN);
			Tool grunt = tool("grunt", "1.0.4", ToolType.PRODUCTION_GRUNT);
			Tool jenkins = tool("jenkins", "3.0.2", ToolType.CI_JENKINS);
			Tool drone = tool("drone", "0.5.0", ToolType.CI_DRONE_IO);
			Tool travis = tool("travis", "1.0.0", ToolType.CI_TRAVIS_CI);
			Tool yarn = tool("yarn", "0.2.0", ToolType.PRODUCTION_YARN);

			// Project 1

			Project project1 = project("pipeline-test", "A project for testing the CI pipeline with various data because why not", "1.2.3",
					maven, "team", null);

			project1.getTeam().getDevelopers()
					.add(developer("ABC", "Milley Dupond", "mil@email.com", Resources.LOGO_ROOT + "/other/demo-1.png"));
			project1.getTeam().getDevelopers()
					.add(developer("BBB", "Lara Dupond", "abbbd@email.com", Resources.LOGO_ROOT + "/other/demo-2.png"));
			project1.getTeam().getDevelopers()
					.add(developer("EEE", "Test Dupond", "assssld@email.com", Resources.LOGO_ROOT + "/other/demo-3.png"));
			project1.getTeam().getDevelopers()
					.add(developer("ATT", "Arnold Test", "attttd@email.com", Resources.LOGO_ROOT + "/other/demo-4.png"));
			project1.getTeam().getDevelopers()
					.add(developer("TTT", "Gerard Menvu", "testld@email.com", Resources.LOGO_ROOT + "/other/demo-5.png"));
			project1.getTeam().getDevelopers()
					.add(developer("RRR", "Cal Cal", "arnrtt@testemail.com", Resources.LOGO_ROOT + "/other/demo-6.png"));
			project1.getTeam().getDevelopers()
					.add(developer("VVV", "Test Test", "arrrtttd@email.com", Resources.LOGO_ROOT + "/other/demo-7.png"));
			project1.getTeam().getDevelopers()
					.add(developer("GTY", "Pierre Dupond", "arnold@email.com", Resources.LOGO_ROOT + "/other/demo-8.png"));
			project1.getTeam().getDevelopers()
					.add(developer("ELE", "Yves Truis", "ytruis@test.com", Resources.LOGO_ROOT + "/other/demo-9.png"));
			project1.getTeam().getDevelopers()
					.add(developer("IBU", "Anna Paula", "anna@testtest.com", Resources.LOGO_ROOT + "/other/demo-10.png"));
			project1.getTeam().getDevelopers()
					.add(developer("IGG", "Georges Abitbol", "gg@testtest.com", Resources.LOGO_ROOT + "/other/demo-11.png"));
			project1.getTeam().getDevelopers()
					.add(developer("KAL", "Kalel Superman", "super@testtest.com", Resources.LOGO_ROOT + "/other/demo-12.png"));
			project1.getTeam().getDevelopers()
					.add(developer("BWA", "Bruce Wayne", "bruw@testtest.com", Resources.LOGO_ROOT + "/other/demo-13.png"));
			project1.getTeam().getDevelopers()
					.add(developer("ALA", "Laly Lalaly", "lllllaaallll@testtest.com", Resources.LOGO_ROOT + "/other/demo-14.png"));

			this.teams.mergeWithExistingAndSave(project1.getTeam());
			this.projects.mergeWithExistingAndSave(project1);

			project1.getResults().add(testResult(20, 0, 0, true, maven, project1));
			project1.getResults().add(testResult(20, 0, 8, false, maven, project1));
			project1.getResults().add(testResult(20, 5, 3, false, maven, project1));
			project1.getResults().add(testResult(25, 1, 2, false, maven, project1));
			project1.getResults().add(testResult(28, 0, 0, true, maven, project1));

			project1.getResults().add(auditResult(23, 10990, true, jenkins, project1));
			project1.getResults().add(auditResult(23, 10990, true, jenkins, project1));
			project1.getResults().add(auditResult(23, 11222, true, jenkins, project1));
			project1.getResults().add(auditResult(25, 12000, false, jenkins, project1));
			project1.getResults().add(auditResult(25, 12009, true, jenkins, project1));
			project1.getResults().add(auditResult(27, 12080, true, jenkins, project1));

			project1.getResults().add(releaseResult(jenkins, project1));

			// Project 1 variations
			this.projects.mergeWithExistingAndSave(project("pipeline-test", null, "1.2.4", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("pipeline-test", null, "2.0.0-alpha", maven, null, "java"));
			this.projects.mergeWithExistingAndSave(project("pipeline-test", null, "1.2.5", maven, null, null));

			// Project 2

			Project project2 = project("my-demo-crm", "A CRM project with some data and fiew developers. Randal made it", "7.0.3", grunt,
					"The web dev warriors", "javascript");

			project2.getTeam().getDevelopers().add(developer("GGG", "Paul Altaruis", "popol@testemail.com", null));
			project2.getTeam().getDevelopers().add(developer("BBB", "Lara Dupond", "abbbd@email.com", null));
			project2.getTeam().getDevelopers()
					.add(developer("RRR", "Cal Cal", "arnrtt@testemail.com", Resources.LOGO_ROOT + "/other/demo-6.png"));
			project2.getTeam().getDevelopers().add(developer("LLL", "Louis Louis", "lllloooolll@testemail.com", null));

			this.teams.mergeWithExistingAndSave(project2.getTeam());
			this.projects.mergeWithExistingAndSave(project2);

			project2.getResults().add(testResult(200, 0, 0, true, grunt, project2));
			project2.getResults().add(testResult(190, 10, 8, false, grunt, project2));
			project2.getResults().add(testResult(200, 5, 5, false, grunt, project2));
			project2.getResults().add(testResult(200, 10, 5, false, grunt, project2));
			project2.getResults().add(testResult(210, 5, 0, true, yarn, project2));
			project2.getResults().add(testResult(215, 5, 10, false, yarn, project2));
			project2.getResults().add(testResult(215, 10, 5, false, grunt, project2));
			project2.getResults().add(testResult(225, 5, 0, true, tool("grunt", "1.0.10", ToolType.PRODUCTION_GRUNT), project2));
			project2.getResults().add(testResult(225, 5, 0, true, tool("grunt", "1.0.10", ToolType.PRODUCTION_GRUNT), project2));

			project2.getResults().add(auditResult(73, 700, true, drone, project2));
			project2.getResults().add(auditResult(75, 701, true, drone, project2));
			project2.getResults().add(auditResult(73, 702, true, drone, project2));

			// Project 2 variations
			this.projects.mergeWithExistingAndSave(project("my-demo-crm", null, "7.0.5", grunt, null, null));
			this.projects.mergeWithExistingAndSave(
					project("my-demo-crm", null, "7.0.6", tool("grunt", "1.0.10", ToolType.PRODUCTION_GRUNT), null, null));

			// Project 3

			Project project3 = project("acme-backend", null, "1.5.0-alpha.1", maven, "The winners", "Java");

			project3.getTeam().getDevelopers().add(developer("ABC", "Leony Yonly", "eoyyy@yyyemail.com", null));
			project3.getTeam().getDevelopers()
					.add(developer("LLL", "Louis Louis", "lllloooolll@testemail.com", Resources.LOGO_ROOT + "/other/demo-13.png"));

			this.teams.mergeWithExistingAndSave(project3.getTeam());
			this.projects.mergeWithExistingAndSave(project3);

			project3.getResults().add(auditResult(0, 1000, false, travis, project3));
			project3.getResults().add(auditResult(0, 1050, false, travis, project3));
			project3.getResults().add(auditResult(0, 1130, false, travis, project3));

			// Project 3 variations
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.5.0-alpha.1", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.5.0-alpha.2", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.5.0-alpha.3", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", "One first specified name", "1.5.0-alpha.4", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.4.45", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.5.0-beta.1", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.5.0-beta.2", maven, null, null));
			this.projects.mergeWithExistingAndSave(
					project("acme-backend", "One other specified name : project for ACME", "1.4.46", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.5.0", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("acme-backend", null, "1.5.1-alpha.1", maven, null, null));

			// Project 4

			Project project4 = project("small-project", "Not a lot to see here", "1.0.0", yarn, "Small", "Node");

			project4.getTeam().getDevelopers()
					.add(developer("ELE", "Yves Truis", "ytruis@test.com", Resources.LOGO_ROOT + "/other/demo-9.png"));

			this.teams.mergeWithExistingAndSave(project4.getTeam());
			this.projects.mergeWithExistingAndSave(project4);

			LOGGER.info("DEMO Data initialized for testing. Now {} project records are available, with {} tools and {} developers",
					Long.valueOf(this.projects.count()),
					Long.valueOf(this.tools.count()),
					Long.valueOf(this.developers.count()));
		}

	}

	/**
	 * @param code
	 * @param name
	 * @param version
	 * @param tool
	 * @param teamName
	 * @return
	 */
	private Project project(String code, String name, String version, Tool tool, String teamName, String language) {

		Project project = new Project();
		project.setCodeName(code);
		project.setCommonName(name);
		project.setVersion(version);
		project.setSemverHash(this.svhg.hashVersion(version));
		project.setLanguage(language);

		project.setProductionTool(tool);

		if (teamName != null) {
			Team team = new Team();
			team.setName(teamName);

			project.setTeam(team);
		}

		return project;
	}

	/**
	 * @param name
	 * @param version
	 * @param type
	 * @return
	 */
	private Tool tool(String name, String version, ToolType type) {

		Tool tool = new Tool();
		tool.setName(name);
		tool.setVersion(version);
		tool.setSemverHash(this.svhg.hashVersion(version));
		tool.setType(type);

		return this.tools.mergeWithExistingAndSave(tool);
	}

	/**
	 * @param trigram
	 * @param name
	 * @param email
	 * @param img
	 * @return
	 */
	private Developer developer(String trigram, String name, String email, String img) {

		Developer developer = new Developer();

		developer.setCompanyName("Test Company");
		developer.setEmail(email);
		developer.setFullname(name);

		if (img != null) {
			developer.setImageUrl(ImageUtils.getImageBase64Uri(ImageUtils.loadImage(img), "png").getBytes());
		}
		developer.setTrigram(trigram);

		return this.developers.mergeWithExistingAndSave(developer);
	}

	/**
	 * @param coverage
	 * @param ncss
	 * @param success
	 * @param tool
	 * @return
	 * @throws JsonProcessingException
	 */
	private Result releaseResult(Tool tool, Project project) throws JsonProcessingException {

		Result result = new Result();
		result.setSuccess(true);
		result.setType(ResultType.RELEASE);
		result.setResultTime(LocalDateTime.now());
		result.setTool(tool);

		result.setProject(project);

		return this.results.save(result);
	}

	/**
	 * @param coverage
	 * @param ncss
	 * @param success
	 * @param tool
	 * @return
	 * @throws JsonProcessingException
	 */
	private Result auditResult(int coverage, int ncss, boolean success, Tool tool, Project project) throws JsonProcessingException {

		Result result = new Result();
		result.setSuccess(success);
		result.setType(ResultType.AUDIT);
		result.setResultTime(LocalDateTime.now());
		result.setTool(tool);

		AuditResultPayloadExtract payload = new AuditResultPayloadExtract();
		payload.setCoverage(coverage);
		payload.setNcss(ncss);

		result.setPayload(generateResultPayload(payload));

		result.setProject(project);

		return this.results.save(result);
	}

	/**
	 * @param successCount
	 * @param ignoredCount
	 * @param failedCount
	 * @param success
	 * @param tool
	 * @return
	 * @throws JsonProcessingException
	 */
	private Result testResult(int successCount, int ignoredCount, int failedCount, boolean success, Tool tool, Project project)
			throws JsonProcessingException {

		Result result = new Result();
		result.setSuccess(success);
		result.setType(ResultType.TEST);
		result.setResultTime(LocalDateTime.now());
		result.setTool(tool);

		TestResultPayloadExtract payload = new TestResultPayloadExtract();
		payload.setFailed(failedCount);
		payload.setSuccess(successCount);
		payload.setIgnored(ignoredCount);

		result.setPayload(generateResultPayload(payload));

		result.setProject(project);

		return this.results.save(result);
	}

	/**
	 * @param extract
	 * @return
	 * @throws JsonProcessingException
	 */
	private String generateResultPayload(ResultPayloadExtract extract) throws JsonProcessingException {
		return this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(extract);
	}

}
