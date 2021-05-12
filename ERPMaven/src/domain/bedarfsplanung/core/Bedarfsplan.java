package domain.bedarfsplanung.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import domain.bedarfsplanung.provided.IBedarfsermittlungService;
import domain.bedarfsplanung.required.IGetFromOrderbuch;
import domain.bedarfsplanung.required.IGetLagerBestand;
import domain.bedarfsplanung.required.ITerminierung;

public class Bedarfsplan {

	private ITerminierung terminierer;
	private IGetFromOrderbuch orderbuch;
	private IGetLagerBestand lagerbestand;

	@Inject
	public Bedarfsplan(ITerminierung t, IGetFromOrderbuch ob) {
		terminierer = t;
		orderbuch = ob;
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
			rueckwaertsErmitteln(bruttobed, start, true);
			LocalDateTime val = kp.getPrimaerbedarf().getEarliestStart();

			if (val.isBefore(start)) {
				start = val;
			}

		}
		if (start.isBefore(LocalDateTime.now())) {
			// vorwaertsterminierung
			
			LocalDateTime curTime = LocalDateTime.now();
			
			//vorwaertsterminieren
			
			
		}

	}

	private void rueckwaertsErmitteln(Bruttobedarf bruttoBed, LocalDateTime ende, boolean sim) {
		
		bruttoBed.setNettobedarf(nettobedarfErmitteln(bruttoBed));

		int aufLager = bruttoBed.getMenge() - bruttoBed.getNettobedarf().getMenge();

		if (aufLager > 0) { // vorhandene Menge reservieren
			//wenn Reservierung vorhanden, dann
			if(bruttoBed.getReservierung() != null) {
				bruttoBed.getReservierung().setMenge(aufLager);
			}else {
			Reservierung r = new Reservierung();
			r.setBruttobedarf(bruttoBed);
			r.setTeil(bruttoBed.getTeil());
			r.setMenge(aufLager);
			bruttoBed.setReservierung(r);
			}
		}
		if (bruttoBed.getNettobedarf().getMenge() != 0) { // nicht alles auf Lager, dann

			if (bruttoBed.getTeil() instanceof Fremdbezugsteil) {
				// Beschaffungsauftrag erstellen
				Fremdbezugsteil fremdteil = (Fremdbezugsteil) bruttoBed.getTeil();
				Beschaffungsauftrag beschAuf = new Beschaffungsauftrag();
				beschAuf.setTeil(fremdteil);
				beschAuf.setMenge(bruttoBed.getNettobedarf().getMenge());
				beschAuf.setEnde(ende);
				//Lieferdauer aus Orderbuch holen // in sekunden ?
				int lieferdauer = orderbuch.getLieferzeit(fremdteil.getTeilenummer()); 
				beschAuf.setStart(ende.minusMinutes(lieferdauer));

				// Einlagerung muss Zeitlich auch geplant werden!!!
				//auch ueber Terminierer
				
				bruttoBed.getNettobedarf().addAuftrag(beschAuf);
				

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
				
				// sekundaerbedarfe ermitteln
				Bruttobedarf sekBed;
				List<Bruttobedarf> sekBedList = new LinkedList<>();

				LocalDateTime curStart = fertauf.getStart();
				for (Stuecklistenelement stLisEle : fertauf.getTeil().getBaukastenstueckliste()) {

					sekBed = new Bruttobedarf();
					sekBed.setTeil(stLisEle.getTeil());
					sekBed.setMenge(stLisEle.getMenge() * fertauf.getMenge());
					sekBedList.add(sekBed);
					rueckwaertsErmitteln(sekBed, curStart, sim); // element ende-termin = start-termin // parent //puffer?
					curStart = sekBed.getEarliestStart(); // keine ablaeufe gleichzeitig!!!
				}
				fertauf.setSekundaerbedarfe(sekBedList);

			} else {
				// Fehler werfen!!! kein Eigenfertigungs- oder Fremdbezugsteil
				System.out.print("das ist keine Kindklasse von Teil");
			}

		}

	}
private void vorwaertsErmitteln(Bruttobedarf bruttoBed, LocalDateTime ende, boolean sim) {
		
		bruttoBed.setNettobedarf(nettobedarfErmitteln(bruttoBed));

		int aufLager = bruttoBed.getMenge() - bruttoBed.getNettobedarf().getMenge();

		if (aufLager > 0) { // vorhandene Menge reservieren
			//wenn Reservierung vorhanden, dann
			if(bruttoBed.getReservierung() != null) {
				bruttoBed.getReservierung().setMenge(aufLager);
			}else {
			Reservierung r = new Reservierung();
			r.setBruttobedarf(bruttoBed);
			r.setTeil(bruttoBed.getTeil());
			r.setMenge(aufLager);
			bruttoBed.setReservierung(r);
			}
		}
		if (bruttoBed.getNettobedarf().getMenge() != 0) { // nicht alles auf Lager, dann

			if (bruttoBed.getTeil() instanceof Fremdbezugsteil) {
				// Beschaffungsauftrag erstellen
				Fremdbezugsteil fremdteil = (Fremdbezugsteil) bruttoBed.getTeil();
				Beschaffungsauftrag beschAuf = new Beschaffungsauftrag();
				beschAuf.setTeil(fremdteil);
				beschAuf.setMenge(bruttoBed.getNettobedarf().getMenge());
				beschAuf.setEnde(ende);
				//Lieferdauer aus Orderbuch holen // in sekunden ?
				int lieferdauer = orderbuch.getLieferzeit(fremdteil.getTeilenummer()); 
				beschAuf.setStart(ende.minusMinutes(lieferdauer));

				// Einlagerung muss Zeitlich auch geplant werden!!!
				//auch ueber Terminierer
				
				bruttoBed.getNettobedarf().addAuftrag(beschAuf);
				

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
				
				// sekundaerbedarfe ermitteln
				Bruttobedarf sekBed;
				List<Bruttobedarf> sekBedList = new LinkedList<>();

				LocalDateTime curStart = fertauf.getStart();
				for (Stuecklistenelement stLisEle : fertauf.getTeil().getBaukastenstueckliste()) {

					sekBed = new Bruttobedarf();
					sekBed.setTeil(stLisEle.getTeil());
					sekBed.setMenge(stLisEle.getMenge() * fertauf.getMenge());
					sekBedList.add(sekBed);
					rueckwaertsErmitteln(sekBed, curStart, sim); // element ende-termin = start-termin // parent //puffer?
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

		int disponierbar = lagerbestand.getLagerbestandFromTeil(bruttoBed.getTeil().getTeilenummer());
		
		//		for (Reservierung r : bruttoBed.getTeil().getReservierungen()) {
//			//Reservierung nicht abziehen, wenn diese zum Bruttobedarf gehoert
//			if(!(r.getBruttobedarf().getBruttobedarfsnummer() == bruttoBed.getBruttobedarfsnummer())) {
//				disponierbar = disponierbar - r.getMenge();
//			}
//			
//		}
		
		nettoBed.setMenge(bruttoBed.getMenge() - disponierbar);
		return nettoBed;
	}

	
}
