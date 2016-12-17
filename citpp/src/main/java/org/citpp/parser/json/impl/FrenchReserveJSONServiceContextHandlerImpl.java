package org.citpp.parser.json.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.citpp.parser.json.JSONServiceContextHandler;
import org.citpp.service.ResourceDownloader;
import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.node.ObjectNode;

public class FrenchReserveJSONServiceContextHandlerImpl implements JSONServiceContextHandler {

	@Override
	public JsonNode handleServiceContext(ServiceContext context, JsonNode node)
			throws JsonProcessingException, IOException {
		String url = (String) context.getParam(ResourceDownloader.RESOURCE_URL_KEY);
		if (StringUtils.isNotBlank(url)) {
			String year = StringUtils.substringAfterLast(url, "/");
			year = StringUtils.substringBefore(year, "_");
			((ObjectNode) node).put("annee", Integer.parseInt(year));
		}
		return node;
	}

}
