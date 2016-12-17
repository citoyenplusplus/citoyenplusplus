package org.citpp.parser.json.impl;

import org.citpp.parser.json.JSONTransformerType;

public class FrenchActeursJSONCleanerImpl extends AbstractStandardJSONCleaner {

	private final JSONPathValues[] values = new JSONPathValues[] {
			new JSONPathValues("mandats.mandat[]", "preseance") };

	@Override
	protected JSONPathValues[] getValuesForTransformerType(JSONTransformerType type) {
		if (JSONTransformerType.STRING_TO_INTEGER.equals(type)) {
			return this.values;
		}
		return null;
	}

}
