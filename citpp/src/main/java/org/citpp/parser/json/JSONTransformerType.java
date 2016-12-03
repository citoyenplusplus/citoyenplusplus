package org.citpp.parser.json;

public enum JSONTransformerType {
	STRING_TO_NULL((map, key) -> {
		if (map.get(key) instanceof String) {
			map.put(key, null);
		}
	}), STRING_TO_INT((map, key) -> {
		Object object = map.get(key);
		map.put(key, Integer.parseInt(object.toString()));
	});
	private final JSONTransformer transformer;

	private JSONTransformerType(JSONTransformer transformer) {
		this.transformer = transformer;
	}

	public JSONTransformer getTransformer() {
		return transformer;
	}

}
