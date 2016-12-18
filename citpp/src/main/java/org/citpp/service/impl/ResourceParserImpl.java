package org.citpp.service.impl;

import java.io.File;
import java.io.IOException;

import org.citpp.parser.json.JSONParser;
import org.citpp.service.ResourceParser;
import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

public class ResourceParserImpl implements ResourceParser, BeanNameAware {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceParserImpl.class);

	private final JSONParser parser;

	private String beanName;

	public ResourceParserImpl(JSONParser parser) {
		super();
		this.parser = parser;
	}

	@Override
	public void execute(ServiceContext context) {
		String sourceFilePath = (String) context.getParam(ResourceParser.INPUT_FILE_PATH_KEY);
		JsonFactory factory = new JsonFactory();
		factory.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		try {
			JsonParser parser = factory.createJsonParser(new File(sourceFilePath));
			JsonToken currentToken = parser.nextToken();
			int tokenCount = 0;
			while (!parser.isClosed()) {
				if (currentToken != null) {
					tokenCount++;
					LOG.trace("{}  parsing token {} ({})", this.beanName, currentToken, tokenCount);
					this.parser.handleToken(context, parser, currentToken);
				}
				currentToken = parser.nextToken();
			}
		} catch (IOException e) {
			LOG.error("{} unable to parse file {}", this.beanName, sourceFilePath, e);
		}
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	protected String getBeanName() {
		return this.beanName;
	}
}
