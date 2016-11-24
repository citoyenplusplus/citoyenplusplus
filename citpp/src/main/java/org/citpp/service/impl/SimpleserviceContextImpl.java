package org.citpp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.citpp.service.ServiceContext;

public class SimpleserviceContextImpl implements ServiceContext {

	private final Map<String, Object> internalMap = new HashMap<>();

	@Override
	public void putParam(String key, Object value) {
		this.internalMap.put(key, value);
	}

	@Override
	public Object getParam(String key) {
		return this.internalMap.get(key);
	}

}
