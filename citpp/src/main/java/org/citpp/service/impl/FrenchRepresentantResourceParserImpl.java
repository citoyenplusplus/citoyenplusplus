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

public class FrenchRepresentantResourceParserImpl extends ResourceParserImpl {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceParserImpl.class);

	public FrenchRepresentantResourceParserImpl(JSONParser parser) {
		super(parser);
	}

	@Override
	public void execute(ServiceContext context) {
		String sourceFilePath = (String) context.getParam(ResourceParser.INPUT_FILE_PATH_KEY);
		String sourceTempFilePath = sourceFilePath + ".temp";
		try {
			Files.write(Paths.get(sourceTempFilePath), "{\"representant\":".getBytes(), StandardOpenOption.CREATE);
			Files.write(Paths.get(sourceTempFilePath), Files.readAllBytes(Paths.get(sourceFilePath)),
					StandardOpenOption.APPEND);
			Files.write(Paths.get(sourceTempFilePath), "}".getBytes(), StandardOpenOption.APPEND);
			Files.move(Paths.get(sourceTempFilePath), Paths.get(sourceFilePath), StandardCopyOption.REPLACE_EXISTING);
		} catch (FileNotFoundException e) {
			LOG.error("{}", this.getBeanName(), e);
		} catch (IOException e) {
			LOG.error("{}", this.getBeanName(), e);
		}
		super.execute(context);
	}

}
