package org.citpp.parser.json.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.citpp.parser.json.JSONCleaner;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class AbstractStandardJSONCleaner implements JSONCleaner {

	@FunctionalInterface
	protected interface JSONTransformer {
		public void transform(Map<String, Object> rootMap, String key);
	}

	protected final static class JSONPathValues {
		private final JSONPath path;
		private final String[] keys;

		public JSONPathValues(String path, String... keys) {
			this.path = new JSONPath(path);
			this.keys = keys;
		}

		public JSONPath getPath() {
			return this.path;
		}

		public String[] getKeys() {
			return keys;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public JsonNode cleanNode(JsonNode node) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> rootMap = (Map<String, Object>) mapper.readValue(node, Map.class);

		this.transform(rootMap, (map, key) -> {
			Object object = map.get(key);
			map.put(key, Integer.parseInt(object.toString()));
		}, this.getStringToIntValues());

		this.transform(rootMap, (map, key) -> {
			if (map.get(key) instanceof String) {
				map.put(key, null);
			}
		}, this.getStringToNullValues());
		this.addSpecificTranforms(rootMap);
		return mapper.valueToTree(rootMap);
	}

	protected void transform(Map<String, Object> rootMap, JSONTransformer transformer, JSONPathValues... pathValues) {
		if (pathValues != null && pathValues.length > 0 && rootMap != null) {
			for (JSONPathValues jsonPathValue : pathValues) {
				List<Map<String, Object>> workingMaps = jsonPathValue.getPath().getObjectsAtPath(rootMap);
				for (String key : jsonPathValue.getKeys()) {
					for (Map<String, Object> workingMap : workingMaps) {
						if (workingMap.containsKey(key)) {
							transformer.transform(workingMap, key);
						}
					}
				}
			}
		}

	}

	protected void addSpecificTranforms(Map<String, Object> rootMap) {

	}

	protected abstract JSONPathValues[] getStringToIntValues();

	protected abstract JSONPathValues[] getStringToNullValues();

}
