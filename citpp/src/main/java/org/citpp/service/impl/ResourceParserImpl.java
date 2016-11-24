package org.citpp.service.impl;

import java.io.File;
import java.io.IOException;

import org.citpp.parser.json.JSONParser;
import org.citpp.service.ResourceParser;
import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceParserImpl implements ResourceParser {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceParserImpl.class);

	private final JSONParser parser;

	public ResourceParserImpl(JSONParser parser) {
		super();
		this.parser = parser;
	}

	@Override
	public void execute(ServiceContext context) {
		String sourceFilePath = (String) context.getParam(ResourceParser.INPUT_FILE_PATH_KEY);
		JsonFactory factory = new JsonFactory();
		try {
			JsonParser parser = factory.createJsonParser(new File(sourceFilePath));
			JsonToken currentToken = parser.nextToken();
			while (!parser.isClosed()) {
				if (currentToken != null) {
					LOG.debug("parsing token {}", currentToken);
					this.parser.handleToken(parser, currentToken);
				}
				currentToken = parser.nextToken();
			}
		} catch (IOException e) {
			LOG.error("unable to parse file {}", sourceFilePath);
		}
	}

}
