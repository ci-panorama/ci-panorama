package fr.elecomte.ci.look.services.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.elecomte.ci.look.services.model.ApplicationVersionView;

/**
 * @author elecomte
 * @since 0.1.0
 */
@RestController
@RequestMapping(Constants.API_ROOT + "/technical")
public class TechnicalRestService {

	@Value("${ci-look.version.id}")
	private String version;

	@Value("${ci-look.version.build}")
	private String build;

	@Value("${ci-look.version.codeName}")
	private String codeName;

	/**
	 * @return
	 */
	@RequestMapping(value = "/ping", method = GET)
	@ResponseBody
	public String ping() {

		return "pong";
	}

	/**
	 * @param projectCodeName
	 * @return
	 */
	@RequestMapping(value = "/version", method = GET)
	@ResponseBody
	public ApplicationVersionView version() {

		return new ApplicationVersionView(this.version, this.codeName, this.build);
	}
}
