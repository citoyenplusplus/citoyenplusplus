package org.citpp.config;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.citpp.parser.index.Indexer;
import org.citpp.parser.json.JSONCleaner;
import org.citpp.parser.json.JSONIDExtractor;
import org.citpp.parser.json.JSONParser;
import org.citpp.parser.json.JSONServiceContextHandler;
import org.citpp.parser.json.impl.AbstractStandardJSONCleaner;
import org.citpp.parser.json.impl.FrenchActeursJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchRepresentantsJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchReserveJSONServiceContextHandlerImpl;
import org.citpp.parser.json.impl.FrenchReunionsJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchVotesJSONCleanerImpl;
import org.citpp.parser.json.impl.JSONParserListImpl;
import org.citpp.parser.json.impl.LeafJSONParserImpl;
import org.citpp.parser.json.impl.PathJSONParserImpl;
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

	@Value("${citpp.parser.amendements.field.1:texteleg,amendements,amendement}")
	private String amendementsFieldName;

	@Value("${citpp.parser.amendements.type:amendement}")
	private String amendementsObjectType;

	@Value("${citpp.parser.reunions.field:reunion}")
	private String reunionsFieldName;

	@Value("${citpp.parser.reunions.type:reunion}")
	private String reunionsObjectType;

	@Value("${citpp.parser.dossiers.id.field.path:dossierParlementaire.uid}")
	private String dossiersIDFieldPath;

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

	@Bean(name = "dossiersIDExtractor")
	public JSONIDExtractor dossiersIDExtractor() {
		return new StandardJSONIDExtractorImpl(this.dossiersIDFieldPath);
	}

	@Bean(name = "frenchActeursJSONCleaner")
	public JSONCleaner frenchActeursJSONCleaner() {
		AbstractStandardJSONCleaner cleaner = new FrenchActeursJSONCleanerImpl();
		return cleaner;
	}

	@Bean(name = "frenchVotesJSONCleaner")
	public JSONCleaner frenchVotesJSONCleaner() {
		AbstractStandardJSONCleaner cleaner = new FrenchVotesJSONCleanerImpl();
		return cleaner;
	}

	@Bean(name = "frenchReunionsJSONCleaner")
	public JSONCleaner frenchReunionsJSONCleaner() {
		AbstractStandardJSONCleaner cleaner = new FrenchReunionsJSONCleanerImpl();
		return cleaner;
	}

	@Bean(name = "frenchRepresentantsJSONCleaner")
	public JSONCleaner frenchRepresentantsJSONCleaner() {
		AbstractStandardJSONCleaner cleaner = new FrenchRepresentantsJSONCleanerImpl();
		return cleaner;
	}

	@Bean(name = "frenchReserveJSONServiceContextHandler")
	public JSONServiceContextHandler frenchReserveJSONServiceContextHandler() {
		return new FrenchReserveJSONServiceContextHandlerImpl();
	}

	@Bean(name = "acteursActeurParser")
	public JSONParser acteursActeurParser() {
		LeafJSONParserImpl acteursParser = new LeafJSONParserImpl(this.indexer, this.frenchActeursJSONCleaner(),
				this.acteursIDExtractor(), null, this.acteursFieldName, acteursObjectType);
		return acteursParser;
	}

	@Bean(name = "acteursOrganeParser")
	public JSONParser acteursOrganeParser() {
		LeafJSONParserImpl organesParser = new LeafJSONParserImpl(this.indexer, null, this.standardIDExtractor(), null,
				this.organesFieldName, this.organesObjectType);
		return organesParser;
	}

	@Bean(name = "acteursParser")
	public JSONParser acteursParser() {
		return new JSONParserListImpl(this.acteursActeurParser(), this.acteursOrganeParser());
	}

	@Bean(name = "votesParser")
	public JSONParser votesParser() {
		LeafJSONParserImpl parser = new LeafJSONParserImpl(this.indexer, this.frenchVotesJSONCleaner(),
				this.standardIDExtractor(), null, this.scrutinsFieldName, this.scrutinsObjectType);
		return parser;
	}

	@Bean(name = "amendementsParser3")
	public JSONParser amendementsParser3() {
		String[] fieldNames = StringUtils.split(this.amendementsFieldName, ",");
		LeafJSONParserImpl parser = new LeafJSONParserImpl(this.indexer, null, this.standardIDExtractor(), null,
				fieldNames[2], this.amendementsObjectType);
		return parser;
	}

	@Bean(name = "amendementsParser2")
	public JSONParser amendementsParser2() {
		String[] fieldNames = StringUtils.split(this.amendementsFieldName, ",");
		PathJSONParserImpl parser = new PathJSONParserImpl(this.amendementsParser3(), fieldNames[1]);
		return parser;
	}

	@Bean(name = "amendementsParser")
	public JSONParser amendementsParser() {
		String[] fieldNames = StringUtils.split(this.amendementsFieldName, ",");
		PathJSONParserImpl parser = new PathJSONParserImpl(this.amendementsParser2(), fieldNames[0]);
		return parser;
	}

	@Bean(name = "reunionsParser")
	public JSONParser reunionsParser() {
		LeafJSONParserImpl parser = new LeafJSONParserImpl(this.indexer, this.frenchReunionsJSONCleaner(),
				this.standardIDExtractor(), null, this.reunionsFieldName, this.reunionsObjectType);
		return parser;
	}

	@Bean(name = "dossiersDossierParser")
	public JSONParser dossiersDossierParser() {
		LeafJSONParserImpl dossiersParser = new LeafJSONParserImpl(this.indexer, null, this.dossiersIDExtractor(), null,
				this.dossiersFieldName, dossiersObjectType);
		return dossiersParser;
	}

	@Bean(name = "dossiersDocumentParser")
	public JSONParser dossiersDocumentParser() {
		LeafJSONParserImpl documentsParser = new LeafJSONParserImpl(this.indexer, null, this.standardIDExtractor(),
				null, this.documentsFieldName, this.documentsObjectType);
		return documentsParser;
	}

	@Bean(name = "dossiersParser")
	public JSONParser dossiersParser() {
		return new JSONParserListImpl(this.dossiersDossierParser(), this.dossiersDocumentParser());
	}

	@Bean(name = "questionsParser")
	public JSONParser questionsParser() {
		LeafJSONParserImpl parser = new LeafJSONParserImpl(this.indexer, null, this.standardIDExtractor(), null,
				this.questionsFieldName, this.questionsObjectType);
		return parser;
	}

	@Bean(name = "reservesParser")
	public JSONParser reservesParser() {
		LeafJSONParserImpl parser = new LeafJSONParserImpl(this.indexer, null, null,
				this.frenchReserveJSONServiceContextHandler(), this.reservesFieldName, this.reservesObjectType);
		return parser;
	}

	@Bean(name = "representantsParser")
	public JSONParser representantsParser() {
		LeafJSONParserImpl parser = new LeafJSONParserImpl(this.indexer, this.frenchRepresentantsJSONCleaner(),
				this.representantsIDExtractor(), null, this.representantsFieldName, this.representantsObjectType);
		return parser;
	}
}
