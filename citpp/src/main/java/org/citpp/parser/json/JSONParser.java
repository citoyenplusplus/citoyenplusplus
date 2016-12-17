package org.citpp.parser.json;

import java.io.IOException;

import org.citpp.service.ServiceContext;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public interface JSONParser {
	public boolean handleToken(ServiceContext context, JsonParser parser, JsonToken token)
			throws JsonParseException, IOException;
}
