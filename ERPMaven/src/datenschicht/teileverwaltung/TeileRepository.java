package datenschicht.teileverwaltung;

import java.util.LinkedList;
import java.util.List;

import domain.teileverwaltung.core.Eigenfertigungsteil;
import domain.teileverwaltung.core.Fremdbezugsteil;
import domain.teileverwaltung.core.Teil;
import domain.teileverwaltung.requiredservices.ITeilHibernate;

public class TeileRepository implements ITeilHibernate {

	private LinkedList<Teil> teileListe;
	private int currId;

	public TeileRepository() {
		teileListe = new LinkedList<Teil>();
		currId = 1;
	}

	@Override
	public boolean createTeil(Teil t) {
		t.setTeilenummer(currId++);
		teileListe.add(t);
		return true;
	}

	@Override
	public List<Teil> getTeil(Teil t) {

		LinkedList<Teil> treffer = new LinkedList<>();
		// zuerst id prüfen, wenn keine id, dann alle Attribut prüfen
		if (t.getTeilenummer() != 0) {
			for (Teil tl : teileListe) {
				if (tl.getTeilenummer() == t.getTeilenummer()) {
					treffer.add(tl);
					return treffer;
				}
			}
		}

		for (Teil tl : teileListe) {
			if (t instanceof Fremdbezugsteil) {
				if (!(tl instanceof Fremdbezugsteil)) {
					continue;
				}
			} else if (t instanceof Eigenfertigungsteil) {
				if (!(tl instanceof Eigenfertigungsteil)) {
					continue;
				}
			}

			if (t.getBezeichnung() != null) {
				if (!tl.getBezeichnung().equalsIgnoreCase(t.getBezeichnung())) {
					continue;
				}
			}

			if (t.getFarbe() != null) {
				if (tl.getFarbe() != t.getFarbe()) {
					continue;
				}
			}
			if (t.getGueltigBis() != null) { // Werte gesetzt
				if (!tl.getGueltigBis().equals(t.getGueltigBis())) { // Wertabweichung vorhanden
					continue; // geht nicht in trefferliste ein
				}
			}
			if (t.getGueltigVon() != null) {
				if (!tl.getGueltigVon().equals(t.getGueltigVon())) {
					continue;
				}
			}
			if (t.getMengeneinheit() != null) {
				if (tl.getMengeneinheit() != t.getMengeneinheit()) {
					continue;
				}
			}
			if (t.getPlanart() != null) {
				if (tl.getPlanart() != t.getPlanart()) {
					continue;
				}
			}
			if (t.getTeileart() != null) {
				if (tl.getTeileart() != t.getTeileart()) {
					continue;
				}
			}
			treffer.add(tl);
		}
		return treffer;
	}

	@Override
	public boolean changeTeil(Teil t) {
		
		for(int i = 0; i<teileListe.size();i++) {
			if(teileListe.get(i).getTeilenummer() == t.getTeilenummer()) {
				teileListe.set(i, t);
				return true;
			}
		}
		return false;
	
	}

	@Override
	public boolean deleteTeil(Teil t) {
		return teileListe.remove(t);

	}

}
