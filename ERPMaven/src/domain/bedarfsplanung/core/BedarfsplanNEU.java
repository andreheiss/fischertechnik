package domain.bedarfsplanung.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import domain.bedarfsplanung.provided.IBedarfsermittlungService;
import domain.bedarfsplanung.required.ITerminierung;

public class BedarfsplanNEU {

	private ITerminierung terminierer;
	private List<Auftrag> aufListe;

	@Inject
	public BedarfsplanNEU(ITerminierung t) {
		terminierer = t;
	}

	void bedarfsermittlungDurchfuehren(Kundenauftrag kdauf, boolean sim) {

		LocalDateTime start;

		if (kdauf.getLieferzeitpunkt() == null) {
			start = LocalDateTime.now();
		}
		start = kdauf.getLieferzeitpunkt();
		Bruttobedarf bruttobed;

		for (Kundenauftragsposition kp : kdauf.getKundenauftragspositionen()) {

			bruttobed = new Bruttobedarf();
			bruttobed.setTeil(kp.getTeil());
			bruttobed.setMenge(kp.getMenge());
			kp.setPrimaerbedarf(bruttobed);
			bedarfErmitteln(bruttobed, start, true);
			LocalDateTime val = kp.getPrimaerbedarf().getEarliestStart();

			if (val.isBefore(start)) {
				start = val;
			}

		}
		if (start.isBefore(LocalDateTime.now())) {
			// vorwaertsterminierung
			
			//Evtl mit Tree machen?
			LocalDateTime curTime = LocalDateTime.now();
			
			int size = aufListe.size();
			Auftrag auf;
			long dauer;
			for(int i = 0;i<size;i++) {
				auf = getMinFromAuftragsliste();
				dauer = auf.getDauer();
				auf.setStart(curTime);
				auf.setEnde(curTime.plusSeconds(dauer));
				curTime = auf.getEnde();  //plus Puffer?
			}
			
		}

	}

	private void bedarfErmitteln(Bruttobedarf bruttoBed, LocalDateTime ende, boolean sim) {

		//fuer vorwaertsterminieren
		aufListe = new LinkedList<>();
		
		bruttoBed.setNettobedarf(nettobedarfErmitteln(bruttoBed));

		int aufLager = bruttoBed.getMenge() - bruttoBed.getNettobedarf().getMenge();

		if (aufLager > 0) { // vorhandene Menge reservieren
			Reservierung r = new Reservierung();
			r.setBruttobedarf(bruttoBed);
			r.setMenge(aufLager);
			bruttoBed.getTeil().reservieren(r);
		}
		if (bruttoBed.getNettobedarf().getMenge() != 0) { // nicht alles auf Lager, dann

			if (bruttoBed.getTeil() instanceof Fremdbezugsteil) {
				// Beschaffungsauftrag erstellen
				Fremdbezugsteil fremdteil = (Fremdbezugsteil) bruttoBed.getTeil();
				Beschaffungsauftrag beschAuf = new Beschaffungsauftrag();
				beschAuf.setMenge(bruttoBed.getNettobedarf().getMenge());
				beschAuf.setEnde(ende);
				// !!!<Terminierung durch Orderbuch> !!!
				int lieferdauer = 5; // in min //uebergangsloesung
				beschAuf.setStart(ende.minusMinutes(lieferdauer));

				// Einlagerung muss Zeitlich auch geplant werden!!!

				beschAuf.setTeil(fremdteil);
				bruttoBed.getNettobedarf().addAuftrag(beschAuf);
				
				//fuer vorwaertsterminieren
				aufListe.add(beschAuf);

			} else if (bruttoBed.getTeil() instanceof Eigenfertigungsteil) {
				// Fertigungsauftrag erstellen
				Eigenfertigungsteil fertigteil = (Eigenfertigungsteil) bruttoBed.getTeil();
				Fertigungsauftrag fertauf = new Fertigungsauftrag();
				fertauf.setTeil(fertigteil);
				fertauf.setMenge(bruttoBed.getNettobedarf().getMenge());
				fertauf.setEnde(ende);

				// kalkurierter Start wird eingetragen
				terminierer.rueckwaertsTerminieren(fertauf);

				bruttoBed.getNettobedarf().addAuftrag(fertauf);
				
				//fuer vorwaertsterminieren
				aufListe.add(fertauf);
				
				// sekundaerbedarfe ermitteln
				Bruttobedarf sekBed;
				List<Bruttobedarf> sekBedList = new LinkedList<>();

				LocalDateTime curStart = fertauf.getStart();
				for (Stuecklistenelement stLisEle : fertauf.getTeil().getBaukastenstueckliste()) {

					sekBed = new Bruttobedarf();
					sekBed.setTeil(stLisEle.getTeil());
					sekBed.setMenge(stLisEle.getMenge() * fertauf.getMenge());
					sekBedList.add(sekBed);
					bedarfErmitteln(sekBed, curStart, sim); // element ende-termin = start-termin // parent //puffer?
					curStart = sekBed.getEarliestStart(); // keine ablaeufe gleichzeitig!!!
				}
				fertauf.setSekundaerbedarfe(sekBedList);

			} else {
				// Fehler werfen!!! kein Eigenfertigungs- oder Fremdbezugsteil
				System.out.print("das ist keine Kindklasse von Teil");
			}

		}

	}

	private Nettobedarf nettobedarfErmitteln(Bruttobedarf bruttoBed) {
		Nettobedarf nettoBed = new Nettobedarf();

		int disponierbar = bruttoBed.getTeil().getLagerbestand();
		for (Reservierung r : bruttoBed.getTeil().getReservierungen()) {
			disponierbar = disponierbar - r.getMenge();
		}
		// todo: pruefen, ob Reservierung fuer diesen Bruttobedarf
		nettoBed.setMenge(bruttoBed.getMenge() - disponierbar);
		return nettoBed;
	}

	private Auftrag getMinFromAuftragsliste() {
		if(aufListe == null || aufListe.size()== 0) {
			throw new RuntimeException("Liste leer");
		}
		LocalDateTime min = aufListe.get(0).getStart();
		int minIdx = 0;
		int idx = 1;
		while(idx++ < aufListe.size()) {
			if(aufListe.get(idx).getStart().isBefore(min)){
				min = aufListe.get(idx).getStart();
				minIdx = idx;
			}
		}
		return aufListe.remove(minIdx);
		
	}
}
