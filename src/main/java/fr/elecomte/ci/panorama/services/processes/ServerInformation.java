package fr.elecomte.ci.panorama.services.processes;

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

	// For cache
	public static final String APP_NAME = "panorama";

	private long startupTime;

	@Value("${panorama.version.id}")
	private String version;

	@Value("${panorama.version.build}")
	private String build;

	@Value("${panorama.version.codeName}")
	private String codeName;

	@Value("${server.contextPath}")
	private String path;
	
	@Value("${server.port}")
	private int port;
	
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
	 * @return the path
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return Integer.valueOf(this.port);
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
