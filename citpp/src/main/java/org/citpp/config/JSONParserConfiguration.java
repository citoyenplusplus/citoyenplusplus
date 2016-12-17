package org.citpp.config;

import javax.annotation.Resource;

import org.citpp.parser.index.Indexer;
import org.citpp.parser.json.JSONCleaner;
import org.citpp.parser.json.JSONIDExtractor;
import org.citpp.parser.json.JSONParser;
import org.citpp.parser.json.JSONServiceContextHandler;
import org.citpp.parser.json.impl.FrenchActeursJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchRepresentantsJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchReserveJSONServiceContextHandlerImpl;
import org.citpp.parser.json.impl.FrenchReunionsJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchVotesJSONCleanerImpl;
import org.citpp.parser.json.impl.JSONParserListImpl;
import org.citpp.parser.json.impl.ObjectArrayJSONParser;
import org.citpp.parser.json.impl.StandardJSONIDExtractorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSONParserConfiguration {

	@Resource(name = "defaultESIndexer")
	private Indexer indexer;

	@Value("${citpp.parser.standard.id.field.path:uid}")
	private String standardIDFieldPath;

	@Value("${citpp.parser.acteurs.id.field.path:uid.#text}")
	private String acteursIDFieldPath;

	@Value("${citpp.parser.acteurs.field:acteur}")
	private String acteursFieldName;

	@Value("${citpp.parser.acteurs.type:acteur}")
	private String acteursObjectType;

	@Value("${citpp.parser.organes.field:organe}")
	private String organesFieldName;

	@Value("${citpp.parser.organes.type:organe}")
	private String organesObjectType;

	@Value("${citpp.parser.scrutins.field:scrutin}")
	private String scrutinsFieldName;

	@Value("${citpp.parser.scrutins.type:scrutin}")
	private String scrutinsObjectType;

	@Value("${citpp.parser.textelegs.field:texteleg}")
	private String textesFieldName;

	@Value("${citpp.parser.textelegs.type:texteleg}")
	private String textesObjectType;

	@Value("${citpp.parser.reunions.field:reunion}")
	private String reunionsFieldName;

	@Value("${citpp.parser.reunions.type:reunion}")
	private String reunionsObjectType;

	@Value("${citpp.parser.dossiers.field:dossier}")
	private String dossiersFieldName;

	@Value("${citpp.parser.dossiers.type:dossier}")
	private String dossiersObjectType;

	@Value("${citpp.parser.documents.field:document}")
	private String documentsFieldName;

	@Value("${citpp.parser.documents.type:document}")
	private String documentsObjectType;

	@Value("${citpp.parser.questions.field:question}")
	private String questionsFieldName;

	@Value("${citpp.parser.questions.type:question}")
	private String questionsObjectType;

	@Value("${citpp.parser.reserves.field:reserve}")
	private String reservesFieldName;

	@Value("${citpp.parser.reserves.type:reserve}")
	private String reservesObjectType;

	@Value("${citpp.parser.representants.id.field.path:Nom}")
	private String representantsIDFieldPath;

	@Value("${citpp.parser.representants.field:representant}")
	private String representantsFieldName;

	@Value("${citpp.parser.representants.type:representant}")
	private String representantsObjectType;

	@Bean(name = "standardIDExtractor")
	public JSONIDExtractor standardIDExtractor() {
		return new StandardJSONIDExtractorImpl(this.standardIDFieldPath);
	}

	@Bean(name = "acteursIDExtractor")
	public JSONIDExtractor acteursIDExtractor() {
		return new StandardJSONIDExtractorImpl(this.acteursIDFieldPath);
	}

	@Bean(name = "representantsIDExtractor")
	public JSONIDExtractor representantsIDExtractor() {
		return new StandardJSONIDExtractorImpl(this.representantsIDFieldPath);
	}

	@Bean(name = "frenchActeursJSONCleaner")
	public JSONCleaner frenchActeursJSONCleaner() {
		return new FrenchActeursJSONCleanerImpl();
	}

	@Bean(name = "frenchVotesJSONCleaner")
	public JSONCleaner frenchVotesJSONCleaner() {
		return new FrenchVotesJSONCleanerImpl();
	}

	@Bean(name = "frenchReunionsJSONCleaner")
	public JSONCleaner frenchReunionsJSONCleaner() {
		return new FrenchReunionsJSONCleanerImpl();
	}

	@Bean(name = "frenchRepresentantsJSONCleaner")
	public JSONCleaner frenchRepresentantsJSONCleaner() {
		return new FrenchRepresentantsJSONCleanerImpl();
	}

	@Bean(name = "frenchReserveJSONServiceContextHandler")
	public JSONServiceContextHandler frenchReserveJSONServiceContextHandler() {
		return new FrenchReserveJSONServiceContextHandlerImpl();
	}

	@Bean(name = "acteursParser")
	public JSONParser acteursParser() {
		JSONParser acteursParser = new ObjectArrayJSONParser(this.indexer, this.frenchActeursJSONCleaner(),
				this.acteursIDExtractor(), null, this.acteursFieldName, acteursObjectType);
		JSONParser organesParser = new ObjectArrayJSONParser(this.indexer, null, this.standardIDExtractor(), null,
				this.organesFieldName, this.organesObjectType);
		return new JSONParserListImpl(acteursParser, organesParser);
	}

	@Bean(name = "votesParser")
	public JSONParser votesParser() {
		return new ObjectArrayJSONParser(this.indexer, this.frenchVotesJSONCleaner(), this.standardIDExtractor(), null,
				this.scrutinsFieldName, this.scrutinsObjectType);
	}

	@Bean(name = "amendementsParser")
	public JSONParser amendementsParser() {
		return new ObjectArrayJSONParser(this.indexer, null, this.standardIDExtractor(), null, this.textesFieldName,
				this.textesObjectType);
	}

	@Bean(name = "reunionsParser")
	public JSONParser reunionsParser() {
		return new ObjectArrayJSONParser(this.indexer, this.frenchReunionsJSONCleaner(), this.standardIDExtractor(),
				null, this.reunionsFieldName, this.reunionsObjectType);
	}

	@Bean(name = "dossiersParser")
	public JSONParser dossiersParser() {
		JSONParser dossiersParser = new ObjectArrayJSONParser(this.indexer, null, this.standardIDExtractor(), null,
				this.dossiersFieldName, dossiersObjectType);
		JSONParser documentsParser = new ObjectArrayJSONParser(this.indexer, null, this.standardIDExtractor(), null,
				this.documentsFieldName, this.documentsObjectType);
		return new JSONParserListImpl(dossiersParser, documentsParser);
	}

	@Bean(name = "questionsParser")
	public JSONParser questionsParser() {
		return new ObjectArrayJSONParser(this.indexer, null, this.standardIDExtractor(), null, this.questionsFieldName,
				this.questionsObjectType);
	}

	@Bean(name = "reservesParser")
	public JSONParser reservesParser() {
		return new ObjectArrayJSONParser(this.indexer, null, null, this.frenchReserveJSONServiceContextHandler(),
				this.reservesFieldName, this.reservesObjectType);
	}

	@Bean(name = "representantsParser")
	public JSONParser representantsParser() {
		return new ObjectArrayJSONParser(this.indexer, this.frenchRepresentantsJSONCleaner(),
				this.representantsIDExtractor(), null, this.representantsFieldName, this.representantsObjectType);
	}
}
