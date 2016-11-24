package org.citpp.process.impl;

import org.citpp.process.Process;
import org.citpp.service.ResourceDownloader;
import org.citpp.service.ResourceParser;
import org.citpp.service.ResourceUnarchiver;
import org.citpp.service.Service;
import org.citpp.service.ServiceContext;
import org.citpp.service.impl.SimpleserviceContextImpl;

public class FrenchOpenDataImportProcessImpl implements Process {

	private final ResourceDownloader downloader;
	private final ResourceUnarchiver unarchiver;
	private final ResourceParser parser;

	private final String resourceURL;
	private final String downloaderPathPattern;
	private final String unarchiverPathPattern;

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
		this.downloader.execute(context);
		String downloadedFilePath = (String) context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY);
		context.putParam(ResourceUnarchiver.INPUT_FILE_PATH_KEY, downloadedFilePath);
		context.putParam(ResourceUnarchiver.UNARCHIVE_PATH_PATTERN_KEY, this.unarchiverPathPattern);
		this.unarchiver.execute(context);
		String unarchivedFilePath = (String) context.getParam(Service.DEFAULT_OUTPUT_STRING_KEY);
		context.putParam(ResourceParser.INPUT_FILE_PATH_KEY, unarchivedFilePath);
		this.parser.execute(context);
	}

}
