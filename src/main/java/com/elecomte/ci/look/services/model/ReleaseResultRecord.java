package com.elecomte.ci.look.services.model;

import com.elecomte.ci.look.data.model.ResultType;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class ReleaseResultRecord extends ResultRecord {

	/**
	 * 
	 */
	public ReleaseResultRecord() {
		super();
	}

	@Override
	public ResultType getType() {
		return ResultType.RELEASE;
	}

}
