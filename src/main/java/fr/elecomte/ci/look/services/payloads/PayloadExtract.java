package fr.elecomte.ci.look.services.payloads;

import java.util.Map;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class PayloadExtract {

	private final Map<String, Object> values;

	/**
	 * @param values
	 */
	PayloadExtract(Map<String, Object> values) {
		this.values = values;
	}

	/**
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		return (T) this.values.get(key);
	}

}
