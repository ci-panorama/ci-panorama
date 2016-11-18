package fr.elecomte.ci.look.services.processes;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Component
public class ServerInformation implements ApplicationContextAware {

	private long startupTime;

	@Value("${ci-look.version.id}")
	private String version;

	@Value("${ci-look.version.build}")
	private String build;

	@Value("${ci-look.version.codeName}")
	private String codeName;

	public ServerInformation() {
		super();
	}

	/**
	 * @param applicationContext
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.startupTime = applicationContext.getStartupDate();
	}

	/**
	 * @return the startupTime
	 */
	public long getStartupTime() {
		return this.startupTime;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @return the build
	 */
	public String getBuild() {
		return this.build;
	}

	/**
	 * @return the codeName
	 */
	public String getCodeName() {
		return this.codeName;
	}

	/**
	 * @return
	 */
	public String getFormatedUptime() {
		long uptime = System.currentTimeMillis() - this.startupTime;

		long days = Math.round(uptime / (3600 * 1000 * 24));
		long hours = Math.round((uptime - days * (3600 * 1000 * 24)) / (3600 * 1000));

		return String.format("%dd, %dh", Long.valueOf(days), Long.valueOf(hours));
	}
}
