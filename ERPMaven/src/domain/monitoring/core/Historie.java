package domain.monitoring.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.shared.MonitoringOrt;
import domain.shared.MonitoringZustand;

public class Historie {

	List<Eintrag> eintraege = new ArrayList<>();

	public void HistorienEintragHinzufuegen(int teilID, MonitoringZustand zustand, MonitoringOrt ort,
			Lagerplatz lagerplatz, LocalDateTime zeitpunkt) {

		int listplace = -1;

		for (int i = 0; i < eintraege.size(); i++) {
			if (teilID == (eintraege.get(i).getTeilID())) {
				listplace = i;
				break;
			}
		}

		if (listplace == -1) {
			Eintrag eintrag = new Eintrag(teilID, zustand, ort, lagerplatz, zeitpunkt);
			eintraege.add(eintrag);
		} else {
			eintraege.get(listplace).EintragZuTeilHinzufuegen(zustand, ort, lagerplatz, zeitpunkt);
		}
	}

	public Eintrag getEintraegeVon(int id) {

		Eintrag res = new Eintrag();

		for (int i = 0; i < eintraege.size(); i++) {
			if (id == eintraege.get(i).getTeilID()) {

				res.setTeilID(id);
				res.setLagerplaetze(eintraege.get(i).getLagerplaetze());
				res.setOrte(eintraege.get(i).getOrte());
				res.setZeitpunkte(eintraege.get(i).getZeitpunkte());
				res.setZustaende(eintraege.get(i).getZustaende());

			}
		}
		return res;
	}

	public Eintrag getLetzterEintragVon(int id) {

		Eintrag e = getEintraegeVon(id);
		Eintrag res = new Eintrag(id, e.getZustaende().get(e.getZustaende().size() - 1),
				e.getOrte().get(e.getOrte().size() - 1), e.getLagerplaetze().get(e.getLagerplaetze().size() - 1),
				e.getZeitpunkte().get(e.getZeitpunkte().size() - 1));

		return res;
	}
}
