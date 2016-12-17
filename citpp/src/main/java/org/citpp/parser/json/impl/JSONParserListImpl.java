package org.citpp.parser.json.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.citpp.parser.json.JSONParser;
import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class JSONParserListImpl implements JSONParser {

	private final List<JSONParser> parsers = new ArrayList<>();

	public JSONParserListImpl(JSONParser... parsers) {
		this.parsers.addAll(Arrays.asList(parsers));
	}

	@Override
	public boolean handleToken(ServiceContext context, JsonParser parser, JsonToken token)
			throws JsonParseException, IOException {
		for (JSONParser jsonParser : parsers) {
			if (jsonParser.handleToken(context, parser, token)) {
				return true;
			}
		}
		return false;
	}

}
