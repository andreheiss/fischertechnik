package datenschicht.betriebsmittelverwaltung;

import java.util.LinkedList;
import java.util.List;

import domain.betriebsmittelverwaltung.core.Betriebsmittel;
import domain.betriebsmittelverwaltung.required.IBetriebsmittelHibernate;
import domain.teileverwaltung.core.Teil;


public class BetriebsmittelRepository implements IBetriebsmittelHibernate{

	private List<Betriebsmittel> bmListe;
	private int currId;
	
	public BetriebsmittelRepository() {
		bmListe = new LinkedList<>();
		currId = 1;
	}
	

	@Override
	public boolean createBetriebsmittel(Betriebsmittel bm) {
		bm.setBetriebsmittelnummer(currId++);
		bmListe.add(bm);
		return true;
	}

	@Override
	public List<Betriebsmittel> getBetriebsmittel(Betriebsmittel bm) {
		
		LinkedList<Betriebsmittel> treffer = new LinkedList<>();
		// zuerst id prüfen, wenn keine id, dann alle Attribut prüfen
		if (bm.getBetriebsmittelnummer() != 0) {
			for (Betriebsmittel bl : bmListe) {
				if (bl.getBetriebsmittelnummer() == bm.getBetriebsmittelnummer()) {
					treffer.add(bl);
					return treffer;
				}
			}
		}

		for (Betriebsmittel bl : bmListe) {

			if (bm.getBezeichnung() != null) {
				if (!bm.getBezeichnung().equalsIgnoreCase(bm.getBezeichnung())) {
					continue;
				}
			}

			if (bm.getArt() != null) {
				if (bm.getArt() != bl.getArt()) {
					continue;
				}
			}
			if (bm.getGueltigBis() != null) { // Werte gesetzt
				if (!bl.getGueltigBis().equals(bm.getGueltigBis())) { // Wertabweichung vorhanden
					continue; // geht nicht in trefferliste ein
				}
			}
			if (bm.getGueltigVon() != null) {
				if (!bl.getGueltigVon().equals(bm.getGueltigVon())) {
					continue;
				}
			}
			if (bm.getWerk() != null) {
				if (!bl.getWerk().equals(bm.getWerk())  ) {
					continue;
				}
			}
			
			treffer.add(bl);
		}
		return treffer;
	}

	@Override
	public boolean changeBetriebsmittel(Betriebsmittel bm) {
		for(int i = 0; i<bmListe.size();i++) {
			if(bmListe.get(i).getBetriebsmittelnummer() == bm.getBetriebsmittelnummer()) {
				bmListe.set(i, bm);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteBetriebsmittel(Betriebsmittel bm) {
		return bmListe.remove(bm);
	}

}
