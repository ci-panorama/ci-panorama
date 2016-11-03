package com.elecomte.ci.look.services.model;

import com.elecomte.ci.look.data.model.ResultType;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class BuildResultRecord extends ResultRecord {

	/**
	 * 
	 */
	public BuildResultRecord() {
		super();
	}

	@Override
	public ResultType getType() {
		return ResultType.BUILD;
	}

}
