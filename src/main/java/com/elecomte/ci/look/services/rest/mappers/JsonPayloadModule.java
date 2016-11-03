package com.elecomte.ci.look.services.rest.mappers;

import java.io.IOException;

import com.elecomte.ci.look.services.model.JsonPayload;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;

/**
 * @author elecomte
 * @since 0.1.0
 */
public class JsonPayloadModule extends SimpleModule {

	/**
	 * 
	 */
	public JsonPayloadModule() {
		super();
		addSerializer(JsonPayload.class, new RawSerializer<JsonPayload>(JsonPayload.class));
		addDeserializer(JsonPayload.class, new JsonPayloadDeserializer());
	}

	/**
	 * @author elecomte
	 * @since 0.1.0
	 */
	public static class JsonPayloadDeserializer extends StdScalarDeserializer<JsonPayload> {

		/**
		 * 
		 */
		public JsonPayloadDeserializer() {
			super(JsonPayload.class);
		}

		/**
		 * @param jsonParser
		 * @param deserializationContext
		 * @return
		 * @throws IOException
		 * @throws JsonProcessingException
		 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser,
		 *      com.fasterxml.jackson.databind.DeserializationContext)
		 */
		@Override
		public JsonPayload deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {

			JsonToken currentToken = jsonParser.getCurrentToken();

			if (currentToken == JsonToken.START_OBJECT) {
				return new JsonPayload(jsonParser.readValueAsTree().toString());
			}

			throw deserializationContext.mappingException(handledType());
		}
	}

}