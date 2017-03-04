package fr.elecomte.ci.panorama.services.model;

import fr.elecomte.ci.panorama.data.model.ResultType;

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
