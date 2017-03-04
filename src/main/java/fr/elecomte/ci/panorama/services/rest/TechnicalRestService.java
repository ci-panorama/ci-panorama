package fr.elecomte.ci.panorama.services.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.elecomte.ci.panorama.services.model.ApplicationVersionView;
import fr.elecomte.ci.panorama.services.model.CacheDetailsView;
import fr.elecomte.ci.panorama.services.processes.ServerInformation;
import fr.elecomte.ci.panorama.services.processes.TechnicalProcess;

/**
 * @author elecomte
 * @since 0.1.0
 */
@RestController
@RequestMapping(Constants.API_ROOT + "/technical")
public class TechnicalRestService {

	@Autowired
	private ServerInformation server;

	@Autowired
	private TechnicalProcess technical;

	/**
	 * @return
	 */
	@RequestMapping(value = "/ping", method = GET)
	@ResponseBody
	public String ping() {

		return "pong";
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/version", method = GET)
	@ResponseBody
	public ApplicationVersionView version() {
		return new ApplicationVersionView(this.server.getVersion(), this.server.getCodeName(), this.server.getBuild());
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/uptime", method = GET)
	@ResponseBody
	public String uptime() {
		return this.server.getFormatedUptime();
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/cache/reset", method = GET)
	@ResponseBody
	public void dropCache() {
		this.technical.clearCache();
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/cache/size", method = GET)
	@ResponseBody
	public CacheDetailsView cacheSize() {
		return this.technical.getCacheDetails();
	}
}
