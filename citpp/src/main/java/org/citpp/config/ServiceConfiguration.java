package org.citpp.config;

import javax.annotation.Resource;

import org.citpp.parser.json.JSONParser;
import org.citpp.service.ResourceDownloader;
import org.citpp.service.ResourceParser;
import org.citpp.service.ResourceUnarchiver;
import org.citpp.service.impl.FrenchRepresentantResourceParserImpl;
import org.citpp.service.impl.FrenchReserveResourceParserImpl;
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

	@Resource(name = "dossiersParser")
	private JSONParser dossiersParser;

	@Resource(name = "questionsParser")
	private JSONParser questionsParser;

	@Resource(name = "reservesParser")
	private JSONParser reservesParser;

	@Resource(name = "representantsParser")
	private JSONParser representantsParser;

	@Bean(name = "defaultDownloader")
	public ResourceDownloader defaultDownloader() {
		ResourceDownloaderImpl downloader = new ResourceDownloaderImpl();
		return downloader;
	}

	@Bean(name = "defaultUnarchiver")
	public ResourceUnarchiver defaultUnarchiver() {
		ResourceUnarchiverImpl unarchiver = new ResourceUnarchiverImpl();
		return unarchiver;
	}

	@Bean(name = "acteursResourceParser")
	public ResourceParser acteurResourceParser() {
		ResourceParserImpl parser = new ResourceParserImpl(this.acteursParser);
		return parser;
	}

	@Bean(name = "votesResourceParser")
	public ResourceParser votesResourceParser() {
		ResourceParserImpl parser = new ResourceParserImpl(this.votesParser);
		return parser;
	}

	@Bean(name = "amendementsResourceParser")
	public ResourceParser amendementsResourceParser() {
		ResourceParserImpl parser = new ResourceParserImpl(this.amendementsParser);
		return parser;
	}

	@Bean(name = "reunionsResourceParser")
	public ResourceParser reunionsResourceParser() {
		ResourceParserImpl parser = new ResourceParserImpl(this.reunionsParser);
		return parser;
	}

	@Bean(name = "dossiersResourceParser")
	public ResourceParser dossiersResourceParser() {
		ResourceParserImpl parser = new ResourceParserImpl(this.dossiersParser);
		return parser;
	}

	@Bean(name = "questionsResourceParser")
	public ResourceParser questionsResourceParser() {
		ResourceParserImpl parser = new ResourceParserImpl(this.questionsParser);
		return parser;
	}

	@Bean(name = "reservesResourceParser")
	public ResourceParser reservesResourceParser() {
		FrenchReserveResourceParserImpl parser = new FrenchReserveResourceParserImpl(this.reservesParser);
		return parser;
	}

	@Bean(name = "representantsResourceParser")
	public ResourceParser representantsResourceParser() {
		FrenchRepresentantResourceParserImpl parser = new FrenchRepresentantResourceParserImpl(
				this.representantsParser);
		return parser;
	}
}
