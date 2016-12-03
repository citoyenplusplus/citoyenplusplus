package org.citpp.config;

import javax.annotation.Resource;

import org.citpp.parser.index.Indexer;
import org.citpp.parser.json.JSONCleaner;
import org.citpp.parser.json.JSONParser;
import org.citpp.parser.json.impl.FrenchActeursJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchReunionsJSONCleanerImpl;
import org.citpp.parser.json.impl.FrenchVotesJSONCleanerImpl;
import org.citpp.parser.json.impl.JSONParserListImpl;
import org.citpp.parser.json.impl.ObjectArrayJSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSONParserConfiguration {

	@Resource(name = "defaultESIndexer")
	private Indexer indexer;

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

	@Bean(name = "acteursParser")
	public JSONParser acteursParser() {
		JSONParser acteursParser = new ObjectArrayJSONParser(this.indexer, this.frenchActeursJSONCleaner(),
				this.acteursFieldName, acteursObjectType);
		JSONParser organesParser = new ObjectArrayJSONParser(this.indexer, null, this.organesFieldName,
				this.organesObjectType);
		return new JSONParserListImpl(acteursParser, organesParser);
	}

	@Bean(name = "votesParser")
	public JSONParser votesParser() {
		return new ObjectArrayJSONParser(this.indexer, this.frenchVotesJSONCleaner(), this.scrutinsFieldName,
				this.scrutinsObjectType);
	}

	@Bean(name = "amendementsParser")
	public JSONParser amendementsParser() {
		return new ObjectArrayJSONParser(this.indexer, null, this.textesFieldName, this.textesObjectType);
	}

	@Bean(name = "reunionsParser")
	public JSONParser reunionsParser() {
		return new ObjectArrayJSONParser(this.indexer, this.frenchReunionsJSONCleaner(), this.reunionsFieldName,
				this.reunionsObjectType);
	}
}
