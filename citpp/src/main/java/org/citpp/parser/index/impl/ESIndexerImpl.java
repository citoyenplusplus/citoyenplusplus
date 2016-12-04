package org.citpp.parser.index.impl;

import java.net.InetSocketAddress;

import org.citpp.parser.index.Indexer;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESIndexerImpl implements Indexer {

	private final static Logger LOG = LoggerFactory.getLogger(ESIndexerImpl.class);

	private final String elasticSearchHost;

	private final int elasticSearchPort;

	private final String elasticSearchIndexName;

	private final String elasticSearchClusterName;

	private TransportClient client;

	public ESIndexerImpl(String elasticSearchHost, int elasticSearchPort, String elasticSearchIndexName,
			String elasticSearchClusterName) {
		this.elasticSearchHost = elasticSearchHost;
		this.elasticSearchPort = elasticSearchPort;
		this.elasticSearchIndexName = elasticSearchIndexName;
		this.elasticSearchClusterName = elasticSearchClusterName;
	}

	@Override
	public void createOrUpdate(String objectType, String objectID, byte[] object) {
		try {
			UpdateResponse response = this.getESClient()
					.prepareUpdate(this.elasticSearchIndexName, objectType, objectID).setDoc(object)
					.setDocAsUpsert(true).get();
			if (response != null) {
				LOG.debug("Update response {}", response);
			}
		} catch (Exception e) {
			LOG.error("{}", e);
		}
	}

	private TransportClient getESClient() {
		if (client == null) {
			client = this.createClient(this.elasticSearchHost, this.elasticSearchPort, this.elasticSearchClusterName);
		}
		return client;
	}

	private TransportClient createClient(String host, int port, String clusterName) {
		LOG.debug("Creating new ES client : cluster name : {} , host {}, port {}", clusterName, host, port);
		Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();
		TransportClient client = TransportClient.builder().settings(settings).build();
		client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(host, port)));
		return client;
	}

}
