package org.citpp.parser.json.impl;

public class FrenchActeursJSONCleanerImpl extends AbstractStandardJSONCleaner {

	private final JSONPathValues[] values = new JSONPathValues[] {
			new JSONPathValues("mandats.mandat[]", "preseance") };

	@Override
	protected JSONPathValues[] getStringToIntValues() {
		return this.values;
	}

	@Override
	protected JSONPathValues[] getStringToNullValues() {
		return null;
	}

}
