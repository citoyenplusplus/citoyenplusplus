package org.citpp.parser.json.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class JSONPath {
	private final String[] path;

	public JSONPath(String path) {
		if (path != null) {
			this.path = StringUtils.split(path, ".");
		} else {
			this.path = new String[] {};
		}
	}

	public List<Map<String, Object>> getObjectsAtPath(Map<String, Object> rootMap) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (rootMap != null) {
			if (this.path.length == 0) {
				resultList.add(rootMap);
			} else {
				resultList.add(rootMap);
				for (String key : path) {
					List<Map<String, Object>> currentResults = new ArrayList<>();
					for (Map<String, Object> currentMap : resultList) {
						currentResults.addAll(this.getElementsForPath(currentMap, key));
					}
					resultList = currentResults;
				}
			}
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getElementsForPath(Map<String, Object> rootMap, String path) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		if (rootMap != null && StringUtils.isNotBlank(path)) {
			if (StringUtils.endsWith(path, "[]")) {
				String realPath = StringUtils.remove(path, "[]");
				if (rootMap.containsKey(realPath)) {
					resultList.addAll((List<Map<String, Object>>) rootMap.get(realPath));
				}
			} else {
				if (rootMap.containsKey(path)) {
					resultList.add((Map<String, Object>) rootMap.get(path));
				}
			}
		}
		return resultList;
	}
}
