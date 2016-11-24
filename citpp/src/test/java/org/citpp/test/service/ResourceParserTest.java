package org.citpp.test.service;

import javax.annotation.Resource;

import org.citpp.service.ResourceParser;
import org.citpp.service.Service;
import org.citpp.service.ServiceContext;
import org.citpp.service.impl.SimpleserviceContextImpl;
import org.citpp.test.app.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ResourceParserTest {
	private final static Logger LOG = LoggerFactory.getLogger(ResourceParserTest.class);
	@Resource(name = "acteursResourceParser")
	private ResourceParser parser;

	@Test
	public void testParseDeputes() {
		ServiceContext context = new SimpleserviceContextImpl();
		context.putParam(ResourceParser.INPUT_FILE_PATH_KEY, "src/test/resources/json/sample-deputes.json");
		LOG.debug("trying to parse file {}", context.getParam(ResourceParser.INPUT_FILE_PATH_KEY));
		this.parser.execute(context);
		Assert.assertNull(context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY));
	}
}