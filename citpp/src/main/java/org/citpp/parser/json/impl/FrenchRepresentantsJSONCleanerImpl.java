package org.citpp.parser.json.impl;

import org.citpp.parser.json.JSONTransformerType;

public class FrenchRepresentantsJSONCleanerImpl extends AbstractStandardJSONCleaner {

	private final JSONPathValues[] valuestoInt = new JSONPathValues[] { new JSONPathValues("", "Nombre de personnes") };

	private final JSONPathValues[] valuestoDouble = new JSONPathValues[] { new JSONPathValues("Données financières",
			"Budget global", "Budget financement public", "Budget financement dons", "Budget financement cotisations",
			"Budget financement commercialisation de produits / services", "Budget financement autres"), };

	@Override
	protected JSONPathValues[] getValuesForTransformerType(JSONTransformerType type) {
		switch (type) {
		case STRING_TO_INTEGER:
			return this.valuestoInt;
		case STRING_TO_FLOAT:
			return this.valuestoDouble;
		default:
			return null;
		}
	}
}
