package org.citpp.parser.json;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

public interface JSONIDExtractor {
	public String extractID(JsonNode node) throws JsonProcessingException, IOException;

	public String extractID(Map<String, Object> rootMap) throws JsonProcessingException, IOException;
}
