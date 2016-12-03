package org.citpp.config;

import javax.annotation.Resource;

import org.citpp.parser.json.JSONParser;
import org.citpp.service.ResourceDownloader;
import org.citpp.service.ResourceParser;
import org.citpp.service.ResourceUnarchiver;
import org.citpp.service.impl.ResourceDownloaderImpl;
import org.citpp.service.impl.ResourceParserImpl;
import org.citpp.service.impl.ResourceUnarchiverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

	@Resource(name = "acteursParser")
	private JSONParser acteursParser;

	@Resource(name = "votesParser")
	private JSONParser votesParser;

	@Resource(name = "amendementsParser")
	private JSONParser amendementsParser;

	@Resource(name = "reunionsParser")
	private JSONParser reunionsParser;

	@Bean(name = "defaultDownloader")
	public ResourceDownloader defaultDownloader() {
		return new ResourceDownloaderImpl();
	}

	@Bean(name = "defaultUnarchiver")
	public ResourceUnarchiver defaultUnarchiver() {
		return new ResourceUnarchiverImpl();
	}

	@Bean(name = "acteursResourceParser")
	public ResourceParser acteurResourceParser() {
		return new ResourceParserImpl(this.acteursParser);
	}

	@Bean(name = "votesResourceParser")
	public ResourceParser votesResourceParser() {
		return new ResourceParserImpl(this.votesParser);
	}

	@Bean(name = "amendementsResourceParser")
	public ResourceParser amendementsResourceParser() {
		return new ResourceParserImpl(this.amendementsParser);
	}
	@Bean(name = "reunionsResourceParser")
	public ResourceParser reunionsResourceParser() {
		return new ResourceParserImpl(this.reunionsParser);
	}
}
