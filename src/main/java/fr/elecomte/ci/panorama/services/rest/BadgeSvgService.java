package fr.elecomte.ci.panorama.services.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.elecomte.ci.panorama.services.processes.BadgeInformationProcess;
import fr.elecomte.ci.panorama.services.processes.ProcessException;

/**
 * @author elecomte
 * @since 0.1.0
 */
@RestController
@RequestMapping(Constants.BADGES_ROOT)
public class BadgeSvgService {

	@Autowired
	private BadgeInformationProcess badges;

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/audit.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String audit(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getAuditBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/audit-coverage.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String auditCoverage(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getAuditCoverageBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/audit-ncss.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String auditNcss(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getAuditNcssBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/build.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String build(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getBuildBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/tool-logo.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String toolLogo(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getToolLogoBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/version.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String version(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getVersionBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/test.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String test(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getTestBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/test-count.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String testCount(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getTestCountBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/test-evolution.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String testEvolution(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getTestEvolutionBadge(projectCodeName, projectVersion);
	}

	/**
	 * @param record
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/{projectCodeName}/{projectVersion}/developers.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String developers(@PathVariable String projectCodeName, @PathVariable String projectVersion) throws ProcessException {
		return this.badges.getProjectDevelopersBadge(projectCodeName, projectVersion);
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/server/version.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String serverVersion() throws ProcessException {
		return this.badges.getServerVersionBadge();
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/server/logo.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String serverLogo() throws ProcessException {
		return this.badges.getServerLogoBadge();
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/server/project-count.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String serverProjectCount() throws ProcessException {
		return this.badges.getServerProjectCountBadge();
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	@RequestMapping(value = "/server/uptime.svg", method = GET, produces = "image/svg+xml ")
	@ResponseBody
	public String serverUptime() throws ProcessException {
		return this.badges.getServerUptimeBadge();
	}
}
