package org.citpp.service;

public interface Service {
	public static final String DEFAULT_OUTPUT_STRING_KEY = "outputString";

	public void execute(ServiceContext context);
}
