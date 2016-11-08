package fr.elecomte.ci.look.services.payloads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.elecomte.ci.look.data.model.Result;
import fr.elecomte.ci.look.data.model.ResultType;
import fr.elecomte.ci.look.services.payloads.extracts.AuditResultPayloadExtract;
import fr.elecomte.ci.look.services.payloads.extracts.BuildResultPayloadExtract;
import fr.elecomte.ci.look.services.payloads.extracts.DeployResultPayloadExtract;
import fr.elecomte.ci.look.services.payloads.extracts.InstallResultPayloadExtract;
import fr.elecomte.ci.look.services.payloads.extracts.ResultPayloadExtract;
import fr.elecomte.ci.look.services.payloads.extracts.TestResultPayloadExtract;

/**
 * @author elecomte
 * @since 0.1.0
 */
@Component
public class PayloadExtractor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayloadExtractor.class);

	public static final Map<ResultType, Class<? extends ResultPayloadExtract>> RESULT_PAYLOAD_TYPES = new HashMap<ResultType, Class<? extends ResultPayloadExtract>>() {
		{
			put(ResultType.TEST, TestResultPayloadExtract.class);
			put(ResultType.AUDIT, AuditResultPayloadExtract.class);
			put(ResultType.DEPLOY, DeployResultPayloadExtract.class);
			put(ResultType.INSTALL, InstallResultPayloadExtract.class);
			put(ResultType.BUILD, BuildResultPayloadExtract.class);
		}

	};

	private ObjectMapper mapper;

	/**
	 * 
	 */
	@PostConstruct
	public void configureMapper() {
		this.mapper = new ObjectMapper();
		this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * @param payload
	 * @param result
	 * @return
	 * @throws IOException
	 */
	public <T extends ResultPayloadExtract> List<T> extractFromResults(List<Result> results) throws IOException {

		List<T> payloads = new ArrayList<>();

		for (Result result : results) {
			T payload = extractFromResult(result);
			if (payload != null) {
				payloads.add(payload);
			}
		}

		return payloads;
	}

	/**
	 * @param payload
	 * @param result
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public <T extends ResultPayloadExtract> T extractFromResult(Result result) throws IOException {

		if (result.getPayload() == null) {
			LOGGER.debug("No Payload");
			return null;
		}

		T extract = this.mapper.readValue(result.getPayload(), (Class<T>) RESULT_PAYLOAD_TYPES.get(result.getType()));
		extract.setAssociatedResult(result);

		LOGGER.debug("Payload of type {} processed from result {}", extract.getClass(), result.getId());

		return extract;
	}

}
