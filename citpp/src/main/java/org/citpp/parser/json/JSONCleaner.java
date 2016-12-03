package org.citpp.parser.json;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

public interface JSONCleaner {
	public Map<String, Object> cleanNode(JsonNode node) throws JsonProcessingException, IOException;
}
