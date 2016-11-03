package com.elecomte.ci.look.services.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elecomte.ci.look.data.model.ToolType;
import com.elecomte.ci.look.services.model.ToolGroupView;
import com.elecomte.ci.look.services.model.ToolView;
import com.elecomte.ci.look.services.processes.ToolInformationProcess;

/**
 * @author elecomte
 * @since 0.1.0
 */
@RestController
@RequestMapping(Constants.API_ROOT + "/tools")
public class ToolRestService {

	@Autowired
	private ToolInformationProcess tools;

	/**
	 * @return
	 */
	@RequestMapping(value = "/groups", method = GET)
	@ResponseBody
	public List<ToolGroupView> groups() {

		return this.tools.getAllExistingToolGroups();
	}

	/**
	 * @param anyType
	 * @return
	 */
	@RequestMapping(value = "/{anyType}", method = GET)
	@ResponseBody
	public List<ToolView> tools(@PathVariable String anyType) {

		return this.tools.getAllToolsInstances(ToolType.detectToolTypeFromValue(anyType.toLowerCase()));
	}

}
