package org.citpp.parser.index;

public interface Indexer {

	public void createOrUpdate(String objectType, String objectID, byte[] object);
}
