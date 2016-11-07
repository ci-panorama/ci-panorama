package fr.elecomte.ci.look.services.processes;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.elecomte.ci.look.data.model.Project;
import fr.elecomte.ci.look.data.model.Result;
import fr.elecomte.ci.look.data.model.ResultType;
import fr.elecomte.ci.look.data.repositories.ResultRepository;
import fr.elecomte.ci.look.services.badges.BadgeType;
import fr.elecomte.ci.look.services.badges.BadgesGenerator;
import fr.elecomte.ci.look.services.payloads.PayloadExtractor;
import fr.elecomte.ci.look.services.payloads.extracts.TestResultPayloadExtract;

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

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getTestCountBadge(String code, String version) throws ProcessException {

		String badge = this.badgesCache.getCachedBadge(code, version, BadgeType.TEST_COUNT.name());

		if (badge == null) {

			try {

				LOGGER.debug("No \"test-count\" cached badge found for project {}/{}. Generate it", code, version);

				Result testResult = getProjectResult(code, version, ResultType.TEST);

				TestResultPayloadExtract payload = testResult != null
						? this.payloadExtractor.extractFromResult(testResult.getPayload(), testResult) : null;

				// If payload found, use payload generated badge, else default test badge
				badge = payload != null
						? this.badgeGenerator.getBadge(BadgeType.TEST_COUNT, payload)
						: this.badgeGenerator.getBadge(BadgeType.TEST, testResult);

				this.badgesCache.putCachedBadge(code, version, BadgeType.TEST_COUNT.name(), badge);

			} catch (IOException e) {
				throw new ProcessException("Cannot process payload", e);
			}
		}

		return badge;
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getTestBadge(String code, String version) throws ProcessException {

		String badge = this.badgesCache.getCachedBadge(code, version, BadgeType.TEST.name());

		if (badge == null) {
			LOGGER.debug("No \"test\" cached badge found for project {}/{}. Generate it", code, version);
			badge = this.badgeGenerator.getBadge(BadgeType.TEST, getProjectResult(code, version, ResultType.TEST));
			this.badgesCache.putCachedBadge(code, version, BadgeType.TEST.name(), badge);
		}

		return badge;
	}

	/**
	 * @param code
	 * @param version
	 * @return
	 * @throws ProcessException
	 */
	public String getBuildBadge(String code, String version) throws ProcessException {

		String badge = this.badgesCache.getCachedBadge(code, version, BadgeType.BUILD.name());

		if (badge == null) {
			LOGGER.debug("No \"build\" cached badge found for project {}/{}. Generate it", code, version);
			badge = this.badgeGenerator.getBadge(BadgeType.BUILD, getProjectResult(code, version, ResultType.BUILD));
			this.badgesCache.putCachedBadge(code, version, BadgeType.BUILD.name(), badge);
		}

		return badge;
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
		} else {
			type = BadgeType.VERSION;
		}

		String badge = this.badgesCache.getCachedBadge(code, version, type.name());

		if (badge == null) {
			LOGGER.debug("No \"version-{}\" cached badge found for project {}/{}. Generate it", type, code, version);
			badge = this.badgeGenerator.getBadge(type, this.projectInfoProcesses.getProject(code, version));
			this.badgesCache.putCachedBadge(code, version, BadgeType.BUILD.name(), badge);
		}

		return badge;

	}

	/**
	 * @param code
	 * @param version
	 * @param type
	 * @return
	 */
	private Result getProjectResult(String code, String version, ResultType type) {

		Project project = this.projectInfoProcesses.getProject(code, version);
		return project != null ? this.results.findFirstByProjectAndTypeOrderByResultTimeDesc(project, type) : null;
	}
}
