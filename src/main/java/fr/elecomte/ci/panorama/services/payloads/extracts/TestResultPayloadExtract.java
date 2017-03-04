package fr.elecomte.ci.panorama.services.payloads.extracts;

import java.util.List;

/**
 * <p>
 * Payload for a test. Model is :
 * 
 * <pre>
 * "payload": {
 * 	"success": {{int nbr of tests in success}},
 * 	"ignored": {{int nbr of tests ignored}},  
 * 	"failed": {{int nbr of tests failed}},  
 * 	"duration": {{int nbr of seconds for test set execution}}
 * }
 * </pre>
 * </p>
 * 
 * @author elecomte
 * @since 0.1.0
 */
public class TestResultPayloadExtract extends ResultPayloadExtract {

	private int success;

	private int ignored;

	private int failed;

	private int duration;

	/**
	 * 
	 */
	public TestResultPayloadExtract() {
		super();
	}

	/**
	 * @return the success
	 */
	public int getSuccess() {
		return this.success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(int success) {
		this.success = success;
	}

	/**
	 * @return the ignored
	 */
	public int getIgnored() {
		return this.ignored;
	}

	/**
	 * @param ignored
	 *            the ignored to set
	 */
	public void setIgnored(int ignored) {
		this.ignored = ignored;
	}

	/**
	 * @return the failed
	 */
	public int getFailed() {
		return this.failed;
	}

	/**
	 * @param failed
	 *            the failed to set
	 */
	public void setFailed(int failed) {
		this.failed = failed;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @param payloads
	 * @return
	 */
	public static int getMaxTotal(List<TestResultPayloadExtract> payloads) {

		int maxTotal = 0;

		for (TestResultPayloadExtract e : payloads) {
			if (e != null) {
				int total = e.getFailed() + e.getSuccess() + e.getIgnored();
				if (total > maxTotal) {
					maxTotal = total;
				}
			}
		}

		return maxTotal;
	}
}
