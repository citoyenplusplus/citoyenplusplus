package org.citpp.parser.json;

import java.util.Map;

@FunctionalInterface
public interface JSONTransformer {
	public void transform(Map<String, Object> rootMap, String key);
}
