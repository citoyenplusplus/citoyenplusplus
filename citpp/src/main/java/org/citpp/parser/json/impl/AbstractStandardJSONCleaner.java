package org.citpp.parser.json.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.citpp.parser.json.JSONCleaner;
import org.citpp.parser.json.JSONTransformer;
import org.citpp.parser.json.JSONTransformerType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.BeanNameAware;

public abstract class AbstractStandardJSONCleaner implements JSONCleaner, BeanNameAware {

	private String beanName;

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
	public Map<String, Object> cleanNode(JsonNode node) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> rootMap = (Map<String, Object>) mapper.readValue(node, Map.class);

		for (JSONTransformerType type : JSONTransformerType.values()) {
			this.transform(rootMap, type.getTransformer(), this.getValuesForTransformerType(type));
		}
		this.addSpecificTranforms(rootMap);
		return rootMap;
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

	protected abstract JSONPathValues[] getValuesForTransformerType(JSONTransformerType type);

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	protected String getBeanName() {
		return this.beanName;
	}

}
