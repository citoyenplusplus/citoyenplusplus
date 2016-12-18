package org.citpp.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.citpp.service.ResourceDownloader;
import org.citpp.service.Service;
import org.citpp.service.ServiceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

public class ResourceDownloaderImpl implements ResourceDownloader, BeanNameAware {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceDownloaderImpl.class);

	private String beanName;

	@Override
	public void execute(ServiceContext context) {
		String URL = (String) context.getParam(ResourceDownloader.RESOURCE_URL_KEY);
		String filePath = new SimpleDateFormat((String) context.getParam(ResourceDownloader.DOWNLOAD_PATH_PATTERN_KEY))
				.format(new Date());
		context.putParam(Service.DEFAULT_OUTPUT_STRING_KEY, filePath);
		try {
			FileUtils.copyURLToFile(new URL(URL), new File(filePath));
		} catch (IOException e) {
			LOG.error("{} unable to download file {}", this.beanName, filePath);
		}

	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
