package org.citpp.parser.json;

import java.io.IOException;

import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

public interface JSONServiceContextHandler {

	public JsonNode handleServiceContext(ServiceContext context, JsonNode node)
			throws JsonProcessingException, IOException;
}
