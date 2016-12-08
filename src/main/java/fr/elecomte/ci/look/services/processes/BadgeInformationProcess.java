package fr.elecomte.ci.look.services.processes;

import static fr.elecomte.ci.look.services.badges.BadgeType.AUDIT;
import static fr.elecomte.ci.look.services.badges.BadgeType.AUDIT_COVERAGE;
import static fr.elecomte.ci.look.services.badges.BadgeType.AUDIT_NCSS;
import static fr.elecomte.ci.look.services.badges.BadgeType.BUILD;
import static fr.elecomte.ci.look.services.badges.BadgeType.TEST;
import static fr.elecomte.ci.look.services.badges.BadgeType.TEST_COUNT;
import static fr.elecomte.ci.look.services.badges.BadgeType.TEST_EVOLUTION;
import static fr.elecomte.ci.look.services.badges.BadgeType.TOOL_LOGO;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.elecomte.ci.look.data.model.Project;
import fr.elecomte.ci.look.data.model.Result;
import fr.elecomte.ci.look.data.model.ResultType;
import fr.elecomte.ci.look.data.model.ToolType;
import fr.elecomte.ci.look.data.repositories.ResultRepository;
import fr.elecomte.ci.look.services.badges.BadgeType;
import fr.elecomte.ci.look.services.badges.BadgesGenerator;
import fr.elecomte.ci.look.services.caches.BadgesCache;
import fr.elecomte.ci.look.services.payloads.PayloadExtractor;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Service
public class BadgeInformationProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(BadgeInformationProcess.class);

	@Autowired
	private ResultRepository results;

	@Autowired
	private ProjectInformationProcess projectInfoProcesses;

	@Autowired
	private BadgesGenerator badgeGenerator;

	@Autowired
	private PayloadExtractor payloadExtractor;

	@Autowired
	private BadgesCache badgesCache;

	@Autowired
	private ServerInformation server;

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getTestCountBadge(String code, String version) throws ProcessException {

		// Get Last TEST payload, will display count of tests in badge
		return this.getCacheableProjectBadge(code, version, TEST_COUNT, null,
				proj -> this.payloadExtractor
						.extractFromResult(this.results.findFirstByProjectAndTypeOrderByResultTimeDesc(proj, ResultType.TEST)));
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getTestEvolutionBadge(String code, String version) throws ProcessException {

		// Get Last 10 TEST payloads, will display evolution of test count
		return this.getCacheableProjectBadge(code, version, TEST_EVOLUTION, null,
				proj -> {
					List<Result> testResults = this.results.findFirst10ByProjectAndTypeOrderByResultTimeDesc(proj, ResultType.TEST);
					return testResults != null && testResults.size() > 0
							? this.payloadExtractor.extractFromResults(testResults) : null;
				});
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getTestBadge(String code, String version) throws ProcessException {

		// Get Last TEST result to display
		return this.getCacheableProjectBadge(code, version, TEST, null,
				proj -> this.results.findFirstByProjectAndTypeOrderByResultTimeDesc(proj, ResultType.TEST));
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getBuildBadge(String code, String version) throws ProcessException {

		// Get Last BUILD result to display
		return this.getCacheableProjectBadge(code, version, BUILD, null,
				proj -> this.results.findFirstByProjectAndTypeOrderByResultTimeDesc(proj, ResultType.BUILD));
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getAuditCoverageBadge(String code, String version) throws ProcessException {

		// Get Last AUDIT payload, will display count of coverage in badge
		return this.getCacheableProjectBadge(code, version, AUDIT_COVERAGE, null,
				proj -> this.payloadExtractor
						.extractFromResult(this.results.findFirstByProjectAndTypeOrderByResultTimeDesc(proj, ResultType.AUDIT)));
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getAuditNcssBadge(String code, String version) throws ProcessException {

		// Get Last AUDIT payload, will display count of ncss in badge
		return this.getCacheableProjectBadge(code, version, AUDIT_NCSS, null,
				proj -> this.payloadExtractor
						.extractFromResult(this.results.findFirstByProjectAndTypeOrderByResultTimeDesc(proj, ResultType.AUDIT)));
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getAuditBadge(String code, String version) throws ProcessException {

		// Get Last AUDIT result, will display status in badge
		return this.getCacheableProjectBadge(code, version, AUDIT, null,
				proj -> this.results.findFirstByProjectAndTypeOrderByResultTimeDesc(proj, ResultType.AUDIT));
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getToolLogoBadge(String code, String version) throws ProcessException {

		// Get Project Tool.ToolType to display, if any
		return this.getCacheableProjectBadge(code, version, TOOL_LOGO, ToolType.PRODUCTION_OTHER,
				proj -> (proj.getProductionTool() != null) ? proj.getProductionTool().getType() : null);
	}

	/**
	 * Version badge is switched regarding the specified version
	 * 
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getVersionBadge(String code, String version) throws ProcessException {

		BadgeType type = null;

		// Rendering depend of required version
		if (version.equals(ProjectInformationProcess.PENDING_VERSION)) {
			type = BadgeType.VERSION_PENDING;
		} else if (version.equals(ProjectInformationProcess.RELEASED_VERSION)) {
			type = BadgeType.VERSION_RELEASED;
		} else if (version.equals(ProjectInformationProcess.FRESH_VERSION)) {
			type = BadgeType.VERSION_FRESH;
		} else if (version.equals(ProjectInformationProcess.LAST_VERSION)) {
			type = BadgeType.VERSION_LAST;
		} else {
			type = BadgeType.VERSION;
		}

		// Get corresponding Project to display version
		return this.getCacheableProjectBadge(code, version, type, null, proj -> proj);
	}

	/**
	 * Get a team overview, if any
	 * 
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getProjectDevelopersBadge(String code, String version) throws ProcessException {

		return this.getCacheableProjectBadge(code, version, BadgeType.DEVELOPER_LIST, null,
				proj -> (proj.getTeam() != null) ? proj.getTeam().getDevelopers() : null);
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	public String getServerUptimeBadge() throws ProcessException {

		// Not cached. Get server uptime
		return this.badgeGenerator.getBadge(BadgeType.SERVER_UPTIME, this.server.getFormatedUptime());
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	public String getServerProjectCountBadge() throws ProcessException {

		// Not cached. Get server count of projects
		return this.badgeGenerator.getBadge(BadgeType.SERVER_PROJECT_COUNT, String.valueOf(this.projectInfoProcesses.getProjectsCount()));
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	public String getServerVersionBadge() throws ProcessException {

		// This one is cached
		String badge = this.badgesCache.getCachedBadge(ServerInformation.APP_NAME, "-", BadgeType.SERVER_VERSION.name());

		if (badge == null) {
			badge = this.badgeGenerator.getBadge(BadgeType.SERVER_VERSION,
					String.format("%s (\"%s\")", this.server.getVersion(), this.server.getCodeName()));
			this.badgesCache.putCachedBadge(ServerInformation.APP_NAME, "-", BadgeType.SERVER_VERSION.name(), badge);
		}

		return badge;
	}

	/**
	 * @return
	 * @throws ProcessException
	 */
	public String getServerLogoBadge() throws ProcessException {

		// This one is cached
		String badge = this.badgesCache.getCachedBadge(ServerInformation.APP_NAME, "-", BadgeType.SERVER_LOGO.name());

		if (badge == null) {
			badge = this.badgeGenerator.getBadge(BadgeType.SERVER_LOGO, ServerInformation.APP_NAME);
			this.badgesCache.putCachedBadge(ServerInformation.APP_NAME, "-", BadgeType.SERVER_LOGO.name(), badge);
		}

		return badge;
	}

	/**
	 * @param code
	 * @param version
	 * @param type
	 * @param defaultResult
	 * @param valueProvider
	 * @return
	 * @throws ProcessException
	 */
	private <T> String getCacheableProjectBadge(String code, String version, BadgeType type, T defaultResult,
			Function<Project, T> valueProvider)
			throws ProcessException {

		String badge = this.badgesCache.getCachedBadge(code, version, type.name());

		if (badge == null) {
			LOGGER.debug("No \"{}\" cached badge found for project {}/{}. Generate it", type.name(), code, version);

			Project proj = this.projectInfoProcesses.getProject(code, version);
			T val = proj != null ? valueProvider.apply(proj) : null;
			badge = this.badgeGenerator.getBadge(type, val != null ? val : defaultResult);
			this.badgesCache.putCachedBadge(code, version, type.name(), badge);
		}

		return badge;
	}

}
