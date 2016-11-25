package org.citpp.parser.json;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;

public interface JSONCleaner {
	public JsonNode cleanNode(JsonNode node) throws JsonProcessingException, IOException;
}
