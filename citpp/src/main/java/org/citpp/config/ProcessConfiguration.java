package org.citpp.config;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.citpp.process.Process;
import org.citpp.process.impl.FrenchOpenDataImportProcessImpl;
import org.citpp.process.impl.ProcessListImpl;
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
	@Value("${citpp.process.acteurs.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/AMO/deputes_senateurs_ministres_legislature/AMO20_dep_sen_min_tous_mandats_et_organes_XIV.json.zip}")
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

	@Resource(name = "dossiersResourceParser")
	private ResourceParser dossiersParser;
	@Value("${citpp.process.dossiers.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/LOI/dossiers_legislatifs/Dossiers_Legislatifs_XIV.json.zip}")
	private String dossiersResourceURL;
	@Value("${citpp.process.dossiers.downloader.path.pattern:'target/zip/dossiers-'YYYY-MM-dd-HH'.zip'}")
	private String dossiersDownloaderPathPattern;
	@Value("${citpp.process.dossiers.unarchiver.path.pattern:'target/json/dossiers-'YYYY-MM-dd-HH'.json'}")
	private String dossiersUnarchiverPathPattern;

	@Resource(name = "questionsResourceParser")
	private ResourceParser questionsParser;
	@Value("${citpp.process.questions.ecrites.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/QUESTIONS/questions_ecrites/Questions_ecrites_XIV.json.zip}")
	private String questionEcritesResourceURL;
	@Value("${citpp.process.questions.ecrites.downloader.path.pattern:'target/zip/questions-ecrites-'YYYY-MM-dd-HH'.zip'}")
	private String questionsEcritesDownloaderPathPattern;
	@Value("${citpp.process.questions.ecrites.unarchiver.path.pattern:'target/json/questions-ecrites-'YYYY-MM-dd-HH'.json'}")
	private String questionsEcritesUnarchiverPathPattern;

	@Value("${citpp.process.questions.gouv.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/QUESTIONS/questions_gouvernement/Questions_gouvernement_XIV.json.zip}")
	private String questionGouvResourceURL;
	@Value("${citpp.process.questions.gouv.downloader.path.pattern:'target/zip/questions-gouv-'YYYY-MM-dd-HH'.zip'}")
	private String questionsGouvDownloaderPathPattern;
	@Value("${citpp.process.questions.gouv.unarchiver.path.pattern:'target/json/questions-gouv-'YYYY-MM-dd-HH'.json'}")
	private String questionsGouvUnarchiverPathPattern;

	@Value("${citpp.process.questions.orales.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/QUESTIONS/questions_orales_sans_debat/Questions_orales_sans_debat_XIV.json.zip}")
	private String questionOralesResourceURL;
	@Value("${citpp.process.questions.orales.downloader.path.pattern:'target/zip/questions-orales-'YYYY-MM-dd-HH'.zip'}")
	private String questionsOralesDownloaderPathPattern;
	@Value("${citpp.process.questions.orales.unarchiver.path.pattern:'target/json/questions-orales-'YYYY-MM-dd-HH'.json'}")
	private String questionsOralesUnarchiverPathPattern;

	@Resource(name = "reservesResourceParser")
	private ResourceParser reservesParser;
	@Value("${citpp.process.reserves.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/RESERVE_PARLEMENTAIRE/{0}_reserve_parlementaire.json.zip}")
	private String reservesResourceURL;
	@Value("${citpp.process.reserves.downloader.path.pattern:'target/zip/reserves-'YYYY-MM-dd-HH'.zip'}")
	private String reservesDownloaderPathPattern;
	@Value("${citpp.process.reserves.unarchiver.path.pattern:'target/json/reserves-'YYYY-MM-dd-HH'.json'}")
	private String reservesUnarchiverPathPattern;

	@Resource(name = "representantsResourceParser")
	private ResourceParser representantsParser;
	@Value("${citpp.process.representants.resource.url:http://data.assemblee-nationale.fr/static/openData/repository/REPRESENTANTS_INTERETS/Representants_interets.json.zip}")
	private String representantsResourceURL;
	@Value("${citpp.process.representants.downloader.path.pattern:'target/zip/representants-'YYYY-MM-dd-HH'.zip'}")
	private String representantsDownloaderPathPattern;
	@Value("${citpp.process.representants.unarchiver.path.pattern:'target/json/representants-'YYYY-MM-dd-HH'.json'}")
	private String representantsUnarchiverPathPattern;

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

	@Bean(name = "dossiersProcess")
	public Process dossiersProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.dossiersParser,
				this.dossiersResourceURL, this.dossiersDownloaderPathPattern, this.dossiersUnarchiverPathPattern);
	}

	@Bean(name = "questionsEcritesProcess")
	public Process questionsEcritesProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.questionsParser,
				this.questionEcritesResourceURL, this.questionsEcritesDownloaderPathPattern,
				this.questionsEcritesUnarchiverPathPattern);
	}

	@Bean(name = "questionsGouvProcess")
	public Process questionsGouvProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.questionsParser,
				this.questionGouvResourceURL, this.questionsGouvDownloaderPathPattern,
				this.questionsGouvUnarchiverPathPattern);
	}

	@Bean(name = "questionsOralesProcess")
	public Process questionsOralesProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.questionsParser,
				this.questionOralesResourceURL, this.questionsOralesDownloaderPathPattern,
				this.questionsOralesUnarchiverPathPattern);
	}

	@Bean(name = "reservesProcess")
	public Process reservesProcess() {
		List<Process> processes = new ArrayList<>();
		MessageFormat format = new MessageFormat(this.reservesResourceURL);
		String[] years = { "2013", "2014", "2015" };
		for (String string : years) {
			processes.add(new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.reservesParser,
					format.format(new String[] { string }),
					StringUtils.replace(this.reservesDownloaderPathPattern, "reserves-", "reserves-" + string + "-"),
					StringUtils.replace(this.reservesUnarchiverPathPattern, "reserves-", "reserves-" + string + "-")));
		}
		return new ProcessListImpl(processes);
	}

	@Bean(name = "representantsProcess")
	public Process representantsProcess() {
		return new FrenchOpenDataImportProcessImpl(this.downloader, this.unarchiver, this.representantsParser,
				this.representantsResourceURL, this.representantsDownloaderPathPattern,
				this.representantsUnarchiverPathPattern);
	}
}
