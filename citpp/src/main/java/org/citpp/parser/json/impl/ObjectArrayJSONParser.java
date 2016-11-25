package org.citpp.parser.json.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.citpp.parser.index.Indexer;
import org.citpp.parser.json.JSONCleaner;
import org.citpp.parser.json.JSONParser;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectArrayJSONParser implements JSONParser {

	private final static Logger LOG = LoggerFactory.getLogger(ObjectArrayJSONParser.class);
	private final JSONCleaner cleaner;
	private final Indexer indexer;
	private final String fieldName;
	private final String objectType;

	public ObjectArrayJSONParser(Indexer indexer, JSONCleaner cleaner, String fieldName, String objectType) {
		this.indexer = indexer;
		this.cleaner = cleaner;
		this.fieldName = fieldName;
		this.objectType = objectType;
	}

	@Override
	public boolean handleToken(JsonParser parser, JsonToken token) throws JsonParseException, IOException {
		if (JsonToken.FIELD_NAME.equals(token)) {
			String currentFieldName = parser.getCurrentName();
			if (StringUtils.equals(this.fieldName, currentFieldName)) {
				while (!JsonToken.START_ARRAY.equals(token)) {
					token = parser.nextToken();
				}
				ObjectMapper mapper = new ObjectMapper();
				while (!JsonToken.END_ARRAY.equals(token)) {
					token = parser.nextToken();
					LOG.debug("parsing token {}", token);
					if (token != null) {
						JsonNode node = mapper.readTree(parser);
						if (this.cleaner != null) {
							node = this.cleaner.cleanNode(node);
						}
						indexer.index(this.objectType, node.toString().getBytes("UTF-8"));
						token = parser.nextToken();
					}
				}
				return true;
			}
		}
		return false;
	}

}
