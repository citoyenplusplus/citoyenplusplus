package org.citpp.parser.json.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.citpp.parser.json.JSONParser;
import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

public class PathJSONParserImpl implements JSONParser, BeanNameAware {

	private final static Logger LOG = LoggerFactory.getLogger(PathJSONParserImpl.class);
	private final String fieldName;

	private final JSONParser parser;

	private String beanName;

	public PathJSONParserImpl(JSONParser parser, String fieldName) {
		this.parser = parser;
		this.fieldName = fieldName;
	}

	@Override
	public boolean handleToken(ServiceContext context, JsonParser parser, JsonToken token)
			throws JsonParseException, IOException {
		if (JsonToken.FIELD_NAME.equals(token)) {
			String currentFieldName = parser.getCurrentName();
			if (StringUtils.equals(this.fieldName, currentFieldName)) {
				while (!JsonToken.START_OBJECT.equals(token) && !JsonToken.START_ARRAY.equals(token)) {
					token = parser.nextToken();
				}
				JsonToken endToken = JsonToken.END_ARRAY;
				if (JsonToken.START_OBJECT.equals(token)) {
					endToken = JsonToken.END_OBJECT;
				}
				token = parser.nextToken();
				int tokenCount = 0;
				while (!endToken.equals(token)) {
					if (token != null) {
						tokenCount++;
						LOG.trace("{} parsing token {} ({})", this.beanName, token, tokenCount);
						try {
							this.parser.handleToken(context, parser, token);
						} catch (Exception e) {
							LOG.error("{}", this.beanName, e);
							throw e;
						}
						token = parser.nextToken();
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
