package org.citpp.config;

import javax.annotation.Resource;

import org.citpp.parser.index.Indexer;
import org.citpp.parser.json.JSONParser;
import org.citpp.parser.json.impl.JSONParserListImpl;
import org.citpp.parser.json.impl.ObjectArrayJSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfiguration {

	@Resource(name = "defaultESIndexer")
	private Indexer indexer;

	@Value("${citpp.parser.acteurs.field:acteur}")
	private String acteursFieldName;

	@Value("${citpp.parser.acteurs.field:acteur}")
	private String acteursObjectType;

	@Value("${citpp.parser.organes.field:organe}")
	private String organesFieldName;

	@Value("${citpp.parser.organes.field:organe}")
	private String organesObjectType;

	@Bean(name = "acteursParser")
	public JSONParser acteursParser() {
		JSONParser acteursParsers = new ObjectArrayJSONParser(this.indexer, this.acteursFieldName, acteursObjectType);
		JSONParser organesParsers = new ObjectArrayJSONParser(this.indexer, this.organesFieldName,
				this.organesObjectType);
		return new JSONParserListImpl(acteursParsers, organesParsers);
	}

}
