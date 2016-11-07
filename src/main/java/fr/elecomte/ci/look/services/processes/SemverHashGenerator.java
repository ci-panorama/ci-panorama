package fr.elecomte.ci.look.services.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.zafarkhaja.semver.Version;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Component
public class SemverHashGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SemverHashGenerator.class);

	/**
	 * @param version
	 * @return
	 */
	public Long hashVersion(String version) {

		Version v = Version.valueOf(version);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Hash generated for version {}. Get {}", version, Integer.valueOf(v.hashCode()));
		}

		return Long.valueOf(v.hashCode());
	}

}
