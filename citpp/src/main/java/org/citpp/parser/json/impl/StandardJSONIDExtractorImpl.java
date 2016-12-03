package org.citpp.parser.json.impl;

import java.io.IOException;
import java.util.Map;

import org.citpp.parser.json.JSONIDExtractor;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class StandardJSONIDExtractorImpl implements JSONIDExtractor {

	private final JSONPath path;

	public StandardJSONIDExtractorImpl(String path) {
		this.path = new JSONPath(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String extractID(JsonNode node) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> rootMap = (Map<String, Object>) mapper.readValue(node, Map.class);
		return this.extractID(rootMap);
	}

	@Override
	public String extractID(Map<String, Object> rootMap) throws JsonProcessingException, IOException {
		Object result = this.path.getFieldAtPath(rootMap);
		if (result != null) {
			return result.toString();
		}
		return null;
	}

}
