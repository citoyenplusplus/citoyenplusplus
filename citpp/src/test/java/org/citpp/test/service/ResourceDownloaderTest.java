package org.citpp.test.service;

import javax.annotation.Resource;

import org.citpp.service.ResourceDownloader;
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
public class ResourceDownloaderTest {
	private final static Logger LOG = LoggerFactory.getLogger(ResourceDownloaderTest.class);
	@Resource
	private ResourceDownloader downloader;

	@Test
	public void testDownloadDeputes() {
		ServiceContext context = new SimpleserviceContextImpl();
		context.putParam(ResourceDownloader.RESOURCE_URL_KEY,
				"http://data.assemblee-nationale.fr/static/openData/repository/AMO/deputes_actifs_mandats_actifs_organes/AMO10_deputes_actifs_mandats_actifs_organes_XIV.json.zip");
		context.putParam(ResourceDownloader.DOWNLOAD_PATH_PATTERN_KEY, "'target/zip/deputes-'YYYY-MM-dd-HH'.zip'");
		this.downloader.execute(context);
		Assert.assertNotNull(context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY));
		LOG.debug("file downloaded at {}", context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY));
	}
}
