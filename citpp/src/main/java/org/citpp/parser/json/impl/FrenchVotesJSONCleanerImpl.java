package org.citpp.parser.json.impl;

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
	protected JSONPathValues[] getStringToIntValues() {
		return this.valuestoInt;
	}

	@Override
	protected JSONPathValues[] getStringToNullValues() {
		return this.valuestoNull;
	}

}
