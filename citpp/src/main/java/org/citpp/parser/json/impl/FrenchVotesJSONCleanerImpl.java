package org.citpp.parser.json.impl;

import org.citpp.parser.json.JSONTransformerType;

public class FrenchVotesJSONCleanerImpl extends AbstractStandardJSONCleaner {

	private final JSONPathValues[] valuestoInt = new JSONPathValues[] {
			new JSONPathValues("", "numero", "quantiemeJourSeance"),
			new JSONPathValues("syntheseVote", "nombreVotants", "suffragesExprimes", "nbrSuffragesRequis"),
			new JSONPathValues("syntheseVote.decompte", "pour", "contre", "abstention", "nonVotant"),
			new JSONPathValues("ventilationVotes.organe.groupes.groupe[]", "nombreMembresGroupe"),
			new JSONPathValues("ventilationVotes.organe.groupes.groupe[].vote.decompteVoix", "pour", "contre",
					"abstention", "nonVotant") };

	private final JSONPathValues[] valuestoNull = new JSONPathValues[] {
			new JSONPathValues("ventilationVotes.organe.groupes.groupe[].vote.decompteNominatif", "nonVotants") };

	@Override
	protected JSONPathValues[] getValuesForTransformerType(JSONTransformerType type) {
		switch (type) {
		case STRING_TO_INT:
			return this.valuestoInt;
		case STRING_TO_NULL:

			return this.valuestoNull;
		default:
			return null;
		}
	}
}
