package domain.bedarfsplanung.core;

import java.time.LocalDateTime;

public class Bruttobedarf {
	
	private int bruttobedarfsnummer;
	private Teil teil;
	private int menge;
	private Nettobedarf nettobedarf;
	
	public int getBruttobedarfsnummer() {
		return bruttobedarfsnummer;
	}
	public void setBruttobedarfsnummer(int bruttobedarfsnummer) {
		this.bruttobedarfsnummer = bruttobedarfsnummer;
	}
	
	public Teil getTeil() {
		return teil;
	}
	public void setTeil(Teil teil) {
		this.teil = teil;
	}
	
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	
	public Nettobedarf getNettobedarf() {
		return nettobedarf;
	}
	public void setNettobedarf(Nettobedarf nettobedarf) {
		this.nettobedarf = nettobedarf;
	}
	
	public LocalDateTime getEarliestStart() {

		LocalDateTime min = null;

		if (this.getNettobedarf() != null || this.getNettobedarf().getMenge() != 0) { // nettobedarf vorhanden und groesser
																					// 0

			if (this.getNettobedarf().getAuftraege().size() != 0) { // hat Auftraege -> falls nicht, ist was falsch
																	// gelaufen

				for (Auftrag a : this.getNettobedarf().getAuftraege()) { // alle Auftraege durchlaufen

					if (min == null || a.getStart().isBefore(min)) { // kein Minimum vorhanden oder Auftrag ist Minimum
						min = a.getStart();
					}
					if (a instanceof Fertigungsauftrag) { // wenn Fertigungsauftrag, dann hat dieser moeglichen
															// Sekundaerbedarf
						Fertigungsauftrag fa = (Fertigungsauftrag) a;

						if (fa.getSekundaerbedarfe().size() != 0) {

							for (Bruttobedarf sk : fa.getSekundaerbedarfe()) {
								LocalDateTime val = sk.getEarliestStart(); // rekursion

								if (val.isBefore(min)) {
									min = val;
								}
							}
						}
					}

				}

			}

		}

		return min;
	}
	
	public LocalDateTime getLastEnd() {

		LocalDateTime max = null;

		if (this.getNettobedarf() != null || this.getNettobedarf().getMenge() != 0) { 
																					

			if (this.getNettobedarf().getAuftraege().size() != 0) { 
																	

				for (Auftrag a : this.getNettobedarf().getAuftraege()) { 

					if (max == null || a.getEnde().isAfter(max)) { 
						max = a.getEnde();
					}
					if (a instanceof Fertigungsauftrag) { 
															
						Fertigungsauftrag fa = (Fertigungsauftrag) a;

						if (fa.getSekundaerbedarfe().size() != 0) {

							for (Bruttobedarf sk : fa.getSekundaerbedarfe()) {
								LocalDateTime val = sk.getLastEnd();

								if (val.isAfter(max)) {
									max = val;
								}
							}
						}
					}

				}

			}

		}

		return max;
	}
	
	
	
}
