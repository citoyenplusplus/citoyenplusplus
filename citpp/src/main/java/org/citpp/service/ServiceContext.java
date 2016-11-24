package org.citpp.service;

public interface ServiceContext {
	public void putParam(String key, Object value);

	public Object getParam(String key);
}
