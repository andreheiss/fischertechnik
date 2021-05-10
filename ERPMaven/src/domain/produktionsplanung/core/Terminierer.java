package domain.produktionsplanung.core;

import com.google.inject.Inject;

import domain.produktionsplanung.required.IGetArbeitsplan;

public class Terminierer {

	private IGetArbeitsplan iap;

	@Inject
	public Terminierer(IGetArbeitsplan iap) {
		this.iap = iap;
	}

	// ohne Beruecksichtigung Belegung und bisheriger Planung
	Produktionsauftrag vorwaertsTerminieren(Produktionsauftrag a) {

		int dauer = kalkuriereDauerFuerFertigungsauftrag(a);
		a.setFertigungsende(a.getFertigungsstart().plusSeconds(dauer));

		return a;
	}

	// ohne Beruecksichtigung Belegung und bisheriger Planung
	Produktionsauftrag rueckwaertsTerminieren(Produktionsauftrag a) {

		int dauer = kalkuriereDauerFuerFertigungsauftrag(a);
		a.setFertigungsstart(a.getFertigungsende().minusSeconds(dauer));

		return a;
	}

	private int kalkuriereDauerFuerFertigungsauftrag(Produktionsauftrag a) {
		
		
		Fertigungsarbeitsplan fap = iap.getArbeitsplanForTeil(a.getTeil().getTeilenummer()); // getArbeitsplanForTeil
		int gesamtdauer = 0; // in sekunden
		for (Fertigungsarbeitsgang ag : fap.getArbeitsgaenge()) {
			gesamtdauer += ag.getRuestzeit() + a.getMenge() * ag.getBearbeitungszeit();
		}
		return gesamtdauer;
	}

}
