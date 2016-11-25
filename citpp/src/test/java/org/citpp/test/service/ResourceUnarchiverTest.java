package org.citpp.test.service;

import javax.annotation.Resource;

import org.citpp.service.ResourceUnarchiver;
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
public class ResourceUnarchiverTest {

	private final static Logger LOG = LoggerFactory.getLogger(ResourceUnarchiverTest.class);
	@Resource
	private ResourceUnarchiver unarchiver;

	@Test
	public void testUnarchiveDeputes() {
		ServiceContext context = new SimpleserviceContextImpl();
		context.putParam(ResourceUnarchiver.INPUT_FILE_PATH_KEY, "src/test/resources/zip/sample-deputes.zip");
		context.putParam(ResourceUnarchiver.UNARCHIVE_PATH_PATTERN_KEY, "'target/json/deputes-'YYYY-MM-dd-HH'.json'");
		this.unarchiver.execute(context);
		Assert.assertNotNull(context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY));
		LOG.debug("file unarchived at {}", context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY));
	}

	@Test
	public void testUnarchiveVotes() {
		ServiceContext context = new SimpleserviceContextImpl();
		context.putParam(ResourceUnarchiver.INPUT_FILE_PATH_KEY, "src/test/resources/zip/sample-votes.zip");
		context.putParam(ResourceUnarchiver.UNARCHIVE_PATH_PATTERN_KEY, "'target/json/votes-'YYYY-MM-dd-HH'.json'");
		this.unarchiver.execute(context);
		Assert.assertNotNull(context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY));
		LOG.debug("file unarchived at {}", context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY));
	}
}
