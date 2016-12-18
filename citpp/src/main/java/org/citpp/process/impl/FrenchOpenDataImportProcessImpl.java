package org.citpp.process.impl;

import org.citpp.process.Process;
import org.citpp.service.ResourceDownloader;
import org.citpp.service.ResourceParser;
import org.citpp.service.ResourceUnarchiver;
import org.citpp.service.Service;
import org.citpp.service.ServiceContext;
import org.citpp.service.impl.SimpleserviceContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

public class FrenchOpenDataImportProcessImpl implements Process, BeanNameAware {

	private static final Logger LOG = LoggerFactory.getLogger(FrenchOpenDataImportProcessImpl.class);
	private final ResourceDownloader downloader;
	private final ResourceUnarchiver unarchiver;
	private final ResourceParser parser;

	private final String resourceURL;
	private final String downloaderPathPattern;
	private final String unarchiverPathPattern;

	private String beanName;

	public FrenchOpenDataImportProcessImpl(ResourceDownloader downloader, ResourceUnarchiver unarchiver,
			ResourceParser parser, String resourceURL, String downloaderPathPattern, String unarchiverPathPattern) {
		this.downloader = downloader;
		this.unarchiver = unarchiver;
		this.parser = parser;
		this.resourceURL = resourceURL;
		this.downloaderPathPattern = downloaderPathPattern;
		this.unarchiverPathPattern = unarchiverPathPattern;
	}

	@Override
	public void execute() {
		ServiceContext context = new SimpleserviceContextImpl();
		context.putParam(ResourceDownloader.RESOURCE_URL_KEY, this.resourceURL);
		context.putParam(ResourceDownloader.DOWNLOAD_PATH_PATTERN_KEY, this.downloaderPathPattern);
		LOG.info("{} starting downloader for resource {} and downloadpath {}", this.getBeanName(), this.resourceURL,
				this.downloaderPathPattern);
		this.downloader.execute(context);
		String downloadedFilePath = (String) context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY);
		context.putParam(ResourceUnarchiver.INPUT_FILE_PATH_KEY, downloadedFilePath);
		context.putParam(ResourceUnarchiver.UNARCHIVE_PATH_PATTERN_KEY, this.unarchiverPathPattern);
		LOG.info("{} starting unarchiver for resource {} and unarchivePath {}", this.getBeanName(), downloadedFilePath,
				this.unarchiverPathPattern);
		this.unarchiver.execute(context);
		String unarchivedFilePath = (String) context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY);
		context.putParam(ResourceParser.INPUT_FILE_PATH_KEY, unarchivedFilePath);
		LOG.info("{} starting parser for resource {}", this.getBeanName(), unarchivedFilePath);
		this.parser.execute(context);
	}

	public String getBeanName() {
		return beanName;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
