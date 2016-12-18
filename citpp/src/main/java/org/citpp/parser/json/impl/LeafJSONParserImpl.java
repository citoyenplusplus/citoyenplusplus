package org.citpp.parser.json.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.citpp.parser.index.Indexer;
import org.citpp.parser.json.JSONCleaner;
import org.citpp.parser.json.JSONIDExtractor;
import org.citpp.parser.json.JSONParser;
import org.citpp.parser.json.JSONServiceContextHandler;
import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

public class LeafJSONParserImpl implements JSONParser, BeanNameAware {

	private final static Logger LOG = LoggerFactory.getLogger(LeafJSONParserImpl.class);
	private final JSONCleaner cleaner;
	private final JSONIDExtractor extractor;
	private final Indexer indexer;
	private final JSONServiceContextHandler serviceContextHandler;
	private final String fieldName;
	private final String objectType;

	private String beanName;

	public LeafJSONParserImpl(Indexer indexer, JSONCleaner cleaner, JSONIDExtractor extractor,
			JSONServiceContextHandler serviceContextHandler, String fieldName, String objectType) {
		this.indexer = indexer;
		this.cleaner = cleaner;
		this.extractor = extractor;
		this.serviceContextHandler = serviceContextHandler;
		this.fieldName = fieldName;
		this.objectType = objectType;
	}

	@Override
	public boolean handleToken(ServiceContext context, JsonParser parser, JsonToken token)
			throws JsonParseException, IOException {
		if (JsonToken.FIELD_NAME.equals(token)) {
			String currentFieldName = parser.getCurrentName();
			if (StringUtils.equals(this.fieldName, currentFieldName)) {
				while (!JsonToken.START_ARRAY.equals(token) && !JsonToken.START_OBJECT.equals(token)) {
					token = parser.nextToken();
				}
				JsonToken endToken = JsonToken.END_ARRAY;
				if (JsonToken.START_OBJECT.equals(token)) {
					endToken = JsonToken.END_OBJECT;
				}
				token = parser.nextToken();
				int objectCreated = 0;
				ObjectMapper mapper = new ObjectMapper();
				while (!endToken.equals(token)) {
					LOG.trace("{} parsing token {}", this.beanName, token);
					if (token != null) {
						JsonNode node = mapper.readTree(parser);
						Map<String, Object> rootMap = null;
						if (this.cleaner != null) {
							rootMap = this.cleaner.cleanNode(node);
						}
						if (this.serviceContextHandler != null) {
							node = this.serviceContextHandler.handleServiceContext(context, node);
						}
						this.addMapToNode(context, node);
						if (this.extractor != null) {
							String objectID = null;
							if (rootMap != null) {
								objectID = this.extractor.extractID(rootMap);
								node = mapper.valueToTree(rootMap);
							} else {
								objectID = this.extractor.extractID(node);
							}
							indexer.createOrUpdate(this.objectType, objectID, node.toString().getBytes("UTF-8"));
							LOG.debug("/// {} : ending creation/update of {} ID {} count {}", this.beanName,
									this.objectType, objectID, objectCreated);
						} else {
							LOG.debug("/// {} : ending creation of {} count {}", this.beanName, this.objectType,
									objectCreated);
							indexer.create(this.objectType, node.toString().getBytes("UTF-8"));
						}

						objectCreated++;
						token = parser.nextToken();
					}
				}
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private void addMapToNode(ServiceContext context, JsonNode node) {
		Object object = context.getParam(CONTEXT_MAP_KEY);
		if (object instanceof Map) {
			Map<String, Object> contextMap = (Map<String, Object>) object;
			for (String key : contextMap.keySet()) {
				((ObjectNode) node).putPOJO(key, contextMap.get(key));
			}
		}
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
