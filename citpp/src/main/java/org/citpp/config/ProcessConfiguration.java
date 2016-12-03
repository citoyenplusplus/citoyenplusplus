package org.citpp.config;

import javax.annotation.Resource;

import org.citpp.process.Process;
import org.citpp.process.impl.FrenchOpenDataImportProcessImpl;
import org.citpp.service.ResourceDownloader;
import org.citpp.service.ResourceParser;
import org.citpp.service.ResourceUnarchiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfiguration {

	@Resource
	private ResourceDownloader downloader;
	@Resource
	private ResourceUnarchiver unarchiver;

	@Resource(name = "acteursResourceParser")
	private ResourceParser acteursParser;
	@Value("${citpp.process.acteurs.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/AMO/deputes_actifs_mandats_actifs_organes/AMO10_deputes_actifs_mandats_actifs_organes_XIV.json.zip}")
	private String acteursResourceURL;
	@Value("${citpp.process.acteurs.downloader.path.pattern:'target/zip/deputes-'YYYY-MM-dd-HH'.zip'}")
	private String acteursDownloaderPathPattern;
	@Value("${citpp.process.acteurs.unarchiver.path.pattern:'target/json/deputes-'YYYY-MM-dd-HH'.json'}")
	private String acteursUnarchiverPathPattern;

	@Resource(name = "votesResourceParser")
	private ResourceParser votesParser;
	@Value("${citpp.process.votes.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/LOI/scrutins/Scrutins_XIV.json.zip}")
	private String votesResourceURL;
	@Value("${citpp.process.votes.downloader.path.pattern:'target/zip/votes-'YYYY-MM-dd-HH'.zip'}")
	private String votesDownloaderPathPattern;
	@Value("${citpp.process.votes.unarchiver.path.pattern:'target/json/votes-'YYYY-MM-dd-HH'.json'}")
	private String votesUnarchiverPathPattern;

	@Resource(name = "amendementsResourceParser")
	private ResourceParser amendementsParser;
	@Value("${citpp.process.amendements.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/LOI/amendements_legis/Amendements_XIV.json.zip}")
	private String amendementsResourceURL;
	@Value("${citpp.process.amendements.downloader.path.pattern:'target/zip/amendements-'YYYY-MM-dd-HH'.zip'}")
	private String amendementsDownloaderPathPattern;
	@Value("${citpp.process.amendements.unarchiver.path.pattern:'target/json/amendements-'YYYY-MM-dd-HH'.json'}")
	private String amendementsUnarchiverPathPattern;

	@Resource(name = "reunionsResourceParser")
	private ResourceParser reunionsParser;
	@Value("${citpp.process.reunions.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/VP/reunions/Agenda_XIV.json.zip}")
	private String reunionsResourceURL;
	@Value("${citpp.process.reunions.downloader.path.pattern:'target/zip/reunions-'YYYY-MM-dd-HH'.zip'}")
	private String reunionsDownloaderPathPattern;
	@Value("${citpp.process.reunions.unarchiver.path.pattern:'target/json/reunions-'YYYY-MM-dd-HH'.json'}")
	private String reunionsUnarchiverPathPattern;

	@Bean(name = "acteursProcess")
	public Process acteursProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.acteursParser,
				this.acteursResourceURL, this.acteursDownloaderPathPattern, this.acteursUnarchiverPathPattern);
	}

	@Bean(name = "votesProcess")
	public Process votesProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.votesParser,
				this.votesResourceURL, this.votesDownloaderPathPattern, this.votesUnarchiverPathPattern);
	}

	@Bean(name = "amendementsProcess")
	public Process amendementsProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.amendementsParser,
				this.amendementsResourceURL, this.amendementsDownloaderPathPattern,
				this.amendementsUnarchiverPathPattern);
	}

	@Bean(name = "reunionsProcess")
	public Process reunionsProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.reunionsParser,
				this.reunionsResourceURL, this.reunionsDownloaderPathPattern, this.reunionsUnarchiverPathPattern);
	}
}
