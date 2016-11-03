package com.elecomte.ci.look.data.model;

import static com.elecomte.ci.look.data.model.ToolFamily.*;

/**
 * @author elecomte
 * @since 0.1.0
 */
public enum ToolType {

	// Production tools
	PRODUCTION_MAVEN(PRODUCTION),
	PRODUCTION_GRADLE(PRODUCTION),
	PRODUCTION_ANT(PRODUCTION),
	PRODUCTION_IVY(PRODUCTION),
	PRODUCTION_MAKE(PRODUCTION),
	PRODUCTION_BUILDR(PRODUCTION),
	PRODUCTION_SBT(PRODUCTION),
	PRODUCTION_OTHER(PRODUCTION),

	// CI Tools
	CI_BAMBOO(CI),
	CI_JENKINS(CI),
	CI_TEAMCITY(CI),
	CI_TRAVIS_CI(CI),
	CI_CIRCLE_CI(CI),
	CI_CODESHIP(CI),
	CI_GITLAB_CI(CI),
	CI_DRONE_IO(CI),
	CI_BITBUCKET(CI),
	CI_OTHER(CI);

	private final ToolFamily family;

	private ToolType(ToolFamily family) {
		this.family = family;
	}

	public ToolFamily getFamily() {
		return this.family;
	}

	/**
	 * @return
	 */
	public String getResourceLogoPath() {
		return Resources.LOGO_ROOT + "/" + name().toLowerCase().replaceFirst("_", "/").replaceAll("_", "-") + Resources.FILE_EXT;
	}

	/**
	 * @param anyValue
	 * @return
	 */
	public static ToolType detectToolTypeFromValue(String anyValue) {
		ToolType ciTry = detectCiToolTypeFromNameVendorName(anyValue);

		if (ciTry == CI_OTHER) {
			return detectProductionToolTypeFromNameVendorName(anyValue);
		}

		return ciTry;
	}

	/**
	 * @param toolNameVendorName
	 * @return
	 */
	private static ToolType detectCiToolTypeFromNameVendorName(String toolNameVendorName) {

		if (toolNameVendorName.contains("jenkins") || toolNameVendorName.contains("hudson")) {
			return CI_JENKINS;
		}

		if (toolNameVendorName.contains("bitbucket")
				|| toolNameVendorName.contains("pipeline") && toolNameVendorName.contains("atlassian")) {
			return CI_BITBUCKET;
		}

		if (toolNameVendorName.contains("bamboo") || toolNameVendorName.contains("atlassian")) {
			return CI_BAMBOO;
		}

		if (toolNameVendorName.contains("circle")) {
			return CI_CIRCLE_CI;
		}

		if (toolNameVendorName.contains("travis")) {
			return CI_TRAVIS_CI;
		}

		if (toolNameVendorName.contains("teamcity")) {
			return CI_TEAMCITY;
		}

		if (toolNameVendorName.contains("drone")) {
			return CI_DRONE_IO;
		}

		if (toolNameVendorName.contains("gitlab")) {
			return CI_GITLAB_CI;
		}

		if (toolNameVendorName.contains("codeship")) {
			return CI_DRONE_IO;
		}

		return CI_OTHER;
	}

	/**
	 * @param toolNameVendorName
	 * @return
	 */
	private static ToolType detectProductionToolTypeFromNameVendorName(String toolNameVendorName) {

		if (toolNameVendorName.contains("maven") || toolNameVendorName.contains("mvn")) {
			return PRODUCTION_MAVEN;
		}

		if (toolNameVendorName.contains("gradle")) {
			return PRODUCTION_GRADLE;
		}

		if (toolNameVendorName.contains("ivy")) {
			return PRODUCTION_IVY;
		}

		if (toolNameVendorName.contains("make")) {
			return PRODUCTION_MAKE;
		}

		if (toolNameVendorName.contains("buildr")) {
			return PRODUCTION_BUILDR;
		}

		if (toolNameVendorName.contains("ant")) {
			return PRODUCTION_ANT;
		}

		if (toolNameVendorName.contains("sbt") || toolNameVendorName.contains("play") || toolNameVendorName.contains("typesafe")
				|| toolNameVendorName.contains("lightbend")) {
			return PRODUCTION_SBT;
		}

		return PRODUCTION_OTHER;
	}

}
