package org.citpp.config;

import org.citpp.parser.index.Indexer;
import org.citpp.parser.index.impl.ESIndexerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexerConfiguration {

	@Value("${citpp.indexer.es.host:localhost}")
	private String elasticSearchHost;
	@Value("${citpp.indexer.es.port:9300}")
	private int elasticSearchPort;
	@Value("${citpp.indexer.es.indexname:citpp}")
	private String elasticSearchIndexName;
	@Value("${citpp.indexer.es.cluster:elasticsearch}")
	private String elasticSearchClusterName;

	@Bean(name = "defaultESIndexer")
	public Indexer defaultESIndexer() {
		return new ESIndexerImpl(elasticSearchHost, elasticSearchPort, elasticSearchIndexName,
				elasticSearchClusterName);
	}
}
