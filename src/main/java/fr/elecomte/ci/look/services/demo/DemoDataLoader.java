package fr.elecomte.ci.look.services.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.elecomte.ci.look.data.model.Developer;
import fr.elecomte.ci.look.data.model.Project;
import fr.elecomte.ci.look.data.model.Team;
import fr.elecomte.ci.look.data.model.Tool;
import fr.elecomte.ci.look.data.model.ToolType;
import fr.elecomte.ci.look.data.repositories.DeveloperRepository;
import fr.elecomte.ci.look.data.repositories.ProjectRepository;
import fr.elecomte.ci.look.data.repositories.TeamRepository;
import fr.elecomte.ci.look.data.repositories.ToolRepository;
import fr.elecomte.ci.look.services.badges.ImageUtils;
import fr.elecomte.ci.look.services.badges.Resources;
import fr.elecomte.ci.look.services.processes.SemverHashGenerator;

/**
 * Data init with demo mode
 * 
 * @author elecomte
 * @since 0.1.0
 */
@Component
@Profile("demo")
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
	private SemverHashGenerator svhg;

	@PostConstruct
	public void launchLoading() {
		processDataLoading();
	}

	@Transactional
	public void processDataLoading() {

		if (this.projects.count() == 0) {

			Project project = new Project();
			project.setCodeName("test");
			project.setVersion("1.2.3");
			project.setSemverHash(this.svhg.hashVersion("1.2.3"));

			Tool tool = new Tool();
			tool.setName("maven");
			tool.setVersion("3.3.1");
			tool.setSemverHash(this.svhg.hashVersion("3.3.1"));
			tool.setType(ToolType.PRODUCTION_MAVEN);

			project.setProductionTool(tool);

			Team team = new Team();
			team.setName("team");

			project.setTeam(team);

			team.getDevelopers().add(developer("ABC", "Milley Dupond", "mil@email.com", Resources.LOGO_ROOT + "/other/demo-1.png"));
			team.getDevelopers().add(developer("BBB", "Lara Dupond", "abbbd@email.com", Resources.LOGO_ROOT + "/other/demo-2.png"));
			team.getDevelopers().add(developer("EEE", "Test Dupond", "assssld@email.com", Resources.LOGO_ROOT + "/other/demo-3.png"));
			team.getDevelopers().add(developer("ATT", "Arnold Test", "attttd@email.com", Resources.LOGO_ROOT + "/other/demo-4.png"));
			team.getDevelopers().add(developer("TTT", "Gerard Menvu", "testld@email.com", Resources.LOGO_ROOT + "/other/demo-5.png"));
			team.getDevelopers().add(developer("RRR", "Cal Cal", "arnrtt@testemail.com", Resources.LOGO_ROOT + "/other/demo-6.png"));
			team.getDevelopers().add(developer("VVV", "Test Test", "arrrtttd@email.com", Resources.LOGO_ROOT + "/other/demo-7.png"));
			team.getDevelopers().add(developer("GTY", "Pierre Dupond", "arnold@email.com", Resources.LOGO_ROOT + "/other/demo-8.png"));
			team.getDevelopers().add(developer("ELE", "Yves Truis", "ytruis@test.com", Resources.LOGO_ROOT + "/other/demo-9.png"));
			team.getDevelopers().add(developer("IBU", "Anna Paula", "anna@testtest.com", Resources.LOGO_ROOT + "/other/demo-10.png"));

			team.getDevelopers().forEach(this.developers::mergeWithExistingAndSave);

			this.tools.mergeWithExistingAndSave(tool);

			this.teams.mergeWithExistingAndSave(team);

			this.projects.mergeWithExistingAndSave(project);

		}
	}

	private static Developer developer(String trigram, String name, String email, String img) {
		Developer developer = new Developer();

		developer.setCompanyName("Test Company");
		developer.setEmail(email);
		developer.setFullname(name);

		if (img != null) {
			developer.setImageUrl(ImageUtils.getImageBase64Uri(ImageUtils.loadImage(img), "png").getBytes());
		}
		developer.setTrigram(trigram);

		return developer;
	}
}
