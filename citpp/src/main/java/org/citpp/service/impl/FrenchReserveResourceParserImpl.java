package org.citpp.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import org.citpp.parser.json.JSONParser;
import org.citpp.service.ResourceParser;
import org.citpp.service.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrenchReserveResourceParserImpl extends ResourceParserImpl {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceParserImpl.class);

	public FrenchReserveResourceParserImpl(JSONParser parser) {
		super(parser);
	}

	@Override
	public void execute(ServiceContext context) {
		String sourceFilePath = (String) context.getParam(ResourceParser.INPUT_FILE_PATH_KEY);
		String sourceTempFilePath = sourceFilePath + ".temp";
		try {
			Files.write(Paths.get(sourceTempFilePath), "{\"reserve\":".getBytes(), StandardOpenOption.CREATE);
			String content = new String(Files.readAllBytes(Paths.get(sourceFilePath)), "UTF-8");
			content = content.replaceAll("NULL", "null");
			Files.write(Paths.get(sourceTempFilePath), content.getBytes("UTF-8"), StandardOpenOption.APPEND);
			Files.write(Paths.get(sourceTempFilePath), "}".getBytes(), StandardOpenOption.APPEND);
			Files.move(Paths.get(sourceTempFilePath), Paths.get(sourceFilePath), StandardCopyOption.REPLACE_EXISTING);
		} catch (FileNotFoundException e) {
			LOG.error("{}", e);
		} catch (IOException e) {
			LOG.error("{}", e);
		}
		super.execute(context);
	}

}
