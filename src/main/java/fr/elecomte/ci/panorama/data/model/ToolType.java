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
package fr.elecomte.ci.panorama.data.model;

import static fr.elecomte.ci.panorama.data.model.ToolFamily.CI;
import static fr.elecomte.ci.panorama.data.model.ToolFamily.PRODUCTION;

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
	PRODUCTION_GRUNT(PRODUCTION),
	PRODUCTION_GULP(PRODUCTION),
	PRODUCTION_NPM(PRODUCTION),
	PRODUCTION_YARN(PRODUCTION),
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

		if (toolNameVendorName.contains("grunt")) {
			return PRODUCTION_GRUNT;
		}

		if (toolNameVendorName.contains("npm")) {
			return PRODUCTION_NPM;
		}

		if (toolNameVendorName.contains("yarn")) {
			return PRODUCTION_YARN;
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
