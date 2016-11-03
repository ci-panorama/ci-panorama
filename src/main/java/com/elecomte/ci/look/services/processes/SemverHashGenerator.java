package com.elecomte.ci.look.services.processes;

import org.springframework.stereotype.Component;

import com.github.zafarkhaja.semver.Version;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Component
public class SemverHashGenerator {

	/**
	 * @param version
	 * @return
	 */
	public Long hashVersion(String version) {

		Version v = Version.valueOf(version);

		System.out.println(version + "=> " + v.hashCode());

		return Long.valueOf(v.hashCode());
	}

}
