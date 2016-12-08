package fr.elecomte.ci.look.services.demo;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.elecomte.ci.look.data.model.Developer;
import fr.elecomte.ci.look.data.model.Project;
import fr.elecomte.ci.look.data.model.Result;
import fr.elecomte.ci.look.data.model.ResultType;
import fr.elecomte.ci.look.data.model.Team;
import fr.elecomte.ci.look.data.model.Tool;
import fr.elecomte.ci.look.data.model.ToolType;
import fr.elecomte.ci.look.data.repositories.DeveloperRepository;
import fr.elecomte.ci.look.data.repositories.ProjectRepository;
import fr.elecomte.ci.look.data.repositories.ResultRepository;
import fr.elecomte.ci.look.data.repositories.TeamRepository;
import fr.elecomte.ci.look.data.repositories.ToolRepository;
import fr.elecomte.ci.look.services.badges.ImageUtils;
import fr.elecomte.ci.look.services.badges.Resources;
import fr.elecomte.ci.look.services.payloads.extracts.AuditResultPayloadExtract;
import fr.elecomte.ci.look.services.payloads.extracts.ResultPayloadExtract;
import fr.elecomte.ci.look.services.payloads.extracts.TestResultPayloadExtract;
import fr.elecomte.ci.look.services.processes.SemverHashGenerator;

/**
 * Data init with demo mode
 * 
 * @author elecomte
 * @since 0.1.0
 */
public class DemoDataLoader {

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

			// Project 1

			Project project1 = project("test", "Project test", "1.2.3", maven, "team", null);

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

			// Project 1 variations
			this.projects.mergeWithExistingAndSave(project("test", null, "1.2.4", maven, null, null));
			this.projects.mergeWithExistingAndSave(project("test", null, "2.0.0-alpha", maven, null, "java"));
			this.projects.mergeWithExistingAndSave(project("test", null, "1.2.5", maven, null, null));

			// Project 2

			Project project2 = project("demo", null, "7.0.3", grunt, "The web dev warriors", "javascript");

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
			project2.getResults().add(testResult(210, 5, 0, true, grunt, project2));
			project2.getResults().add(testResult(215, 5, 10, false, grunt, project2));
			project2.getResults().add(testResult(215, 10, 5, false, grunt, project2));
			project2.getResults().add(testResult(225, 5, 0, true, tool("grunt", "1.0.10", ToolType.PRODUCTION_GRUNT), project2));
			project2.getResults().add(testResult(225, 5, 0, true, tool("grunt", "1.0.10", ToolType.PRODUCTION_GRUNT), project2));

			project2.getResults().add(auditResult(73, 700, true, drone, project2));
			project2.getResults().add(auditResult(75, 701, true, drone, project2));
			project2.getResults().add(auditResult(73, 702, true, drone, project2));

			// Project 2 variations
			this.projects.mergeWithExistingAndSave(project("demo", null, "7.0.5", grunt, null, null));
			this.projects.mergeWithExistingAndSave(
					project("demo", null, "7.0.6", tool("grunt", "1.0.10", ToolType.PRODUCTION_GRUNT), null, null));
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
