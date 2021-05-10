package datenschicht.arbeitsplanverwaltung;

import java.util.LinkedList;
import java.util.List;

import domain.arbeitsplanverwaltung.core.Arbeitsgang;
import domain.arbeitsplanverwaltung.required.IArbeitsgangHibernate;


public class ArbeitsgangRepository implements IArbeitsgangHibernate {

	private LinkedList<Arbeitsgang> agListe;
	private int currId;

	public ArbeitsgangRepository() {
		agListe = new LinkedList<Arbeitsgang>();
		currId = 1;
	}

	@Override
	public boolean createArbeitsgang(Arbeitsgang ag) {
		ag.setArbeitsgangnummer(currId++);
		agListe.add(ag);
		return true;
	}

	@Override
	public List<Arbeitsgang> getArbeitsgang(Arbeitsgang ag) {

		LinkedList<Arbeitsgang> treffer = new LinkedList<>();
		// zuerst id prüfen, wenn keine id, dann alle Attribut prüfen
		if (ag.getArbeitsgangnummer() != 0) {
			for (Arbeitsgang tl : agListe) {
				if (tl.getArbeitsgangnummer() == ag.getArbeitsgangnummer()) {
					treffer.add(tl);
					return treffer;
				}
			}
		}

		for (Arbeitsgang tl : agListe) {
			if (ag.getBezeichnung() != null) {
				if (!tl.getBezeichnung().equalsIgnoreCase(ag.getBezeichnung())) {
					continue;
				}
			}

			if (ag.getBearbeitungszeit() < 0 ) {
				if (tl.getBearbeitungszeit() != ag.getBearbeitungszeit()) {
					continue;
				}
			}
			if (ag.getGueltigBis() != null) { // Werte gesetzt
				if (!tl.getGueltigBis().equals(ag.getGueltigBis())) { // Wertabweichung vorhanden
					continue; // geht nicht in trefferliste ein
				}
			}
			if (ag.getGueltigVon() != null) {
				if (!tl.getGueltigVon().equals(ag.getGueltigVon())) {
					continue;
				}
			}
			if (ag.getRuestzeit() < 0) {
				if (tl.getRuestzeit() != ag.getRuestzeit()) {
					continue;
				}
			}
			treffer.add(tl);
		}
		return treffer;
	}

	@Override
	public boolean changeArbeitsgang(Arbeitsgang ag) {
		
		for(int i = 0; i<agListe.size();i++) {
			if(agListe.get(i).getArbeitsgangnummer() == ag.getArbeitsgangnummer()) {
				agListe.set(i, ag);
				return true;
			}
		}
		return false;
	
	}

	@Override
	public boolean deleteArbeitsgang(Arbeitsgang ag) {
		return agListe.remove(ag);

	}

}
