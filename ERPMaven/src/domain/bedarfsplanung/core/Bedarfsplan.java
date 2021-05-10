package domain.bedarfsplanung.core;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import domain.bedarfsplanung.provided.IBedarfsermittlungService;
import domain.bedarfsplanung.required.ITerminierung;

public class Bedarfsplan {

	private ITerminierung terminierer;

	@Inject
	public Bedarfsplan(ITerminierung t) {
		terminierer = t;
	}

	public Bedarfsplan() {
		// Zum testen, muss wieder entfernt werden !!!!
	}

	void bedarfsermittlungDurchfuehren(Kundenauftrag kdauf, boolean sim) {

		LocalDateTime start;
		
		if (kdauf.getLieferzeitpunkt() != null) { // Termin gesetzt
			// rueckwaertsterminierung
			
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
			if(start.isBefore(LocalDateTime.now()) || kdauf.getLieferzeitpunkt() == null){
				// vorwaertsterminierung
				
				
			}
			

		}
	}

	// pauschal rueckwaerts kalkulieren, wenn < heute, dann vorwaerts
	private void bedarfErmitteln(Bruttobedarf bruttoBed, LocalDateTime ende, boolean sim) {

		//uebler Fehler sachen koennen nicht zeitgleich erfolgen!!!
		
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

				beschAuf.setTeil(fremdteil);
				bruttoBed.getNettobedarf().addAuftrag(beschAuf);

			} else if (bruttoBed.getTeil() instanceof Eigenfertigungsteil) {
				// Fertigungsauftrag erstellen
				Eigenfertigungsteil fertigteil = (Eigenfertigungsteil) bruttoBed.getTeil();
				Fertigungsauftrag fertauf = new Fertigungsauftrag();
				fertauf.setTeil(fertigteil);
				fertauf.setMenge(bruttoBed.getNettobedarf().getMenge());
				fertauf.setEnde(ende);

				// calculated start will be adjusted
				terminierer.rueckwaertsTerminieren(fertauf);

				bruttoBed.getNettobedarf().addAuftrag(fertauf);
				// sekundaerbedarfe ermitteln

				Bruttobedarf sekBed;
				List<Bruttobedarf> sekBedList = new LinkedList<>();

				for (Stuecklistenelement stLisEle : fertauf.getTeil().getBaukastenstueckliste()) {

					sekBed = new Bruttobedarf();
					sekBed.setTeil(stLisEle.getTeil());
					sekBed.setMenge(stLisEle.getMenge() * fertauf.getMenge());
					sekBedList.add(sekBed);
					bedarfErmitteln(sekBed, fertauf.getStart(), sim); // element ende-termin = start-termin // parent //puffer?
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

//	public static void main(String[] args) {
//		
//		List<Lagerplatz> lpl = new LinkedList<Lagerplatz>();
//		lpl.add(new Lagerplatz());
//		lpl.add(new Lagerplatz());
//		Fremdbezugsteil ft = new Fremdbezugsteil();
//		ft.setLagerplaetze(lpl);
//		Eigenfertigungsteil t = new Eigenfertigungsteil();
//		Stuecklistenelement sle = new Stuecklistenelement();
//		sle.setMenge(2);
//		sle.setTeil(ft);
//		List<Stuecklistenelement> bkstl = new LinkedList<>();
//		bkstl.add(sle);
//		t.setBaukastenstueckliste(bkstl);
//
//		Kundenauftragsposition kp = new Kundenauftragsposition();
//		kp.setKundenauftragspositionsnummer(1);
//		kp.setTeil(t);
//		kp.setMenge(2);
//		Kundenauftrag ka = new Kundenauftrag();
//		ka.setKundenauftragsnummer(1);
//		ka.setLieferdatum(LocalDateTime.of(2021, 5, 10, 16,0));
//		List<Kundenauftragsposition> kpos = new LinkedList<>();
//		kpos.add(kp);
//		ka.setKundenauftragspositionen(kpos);
//
//		Bedarfsplan ibs = new Bedarfsplan();
//		ibs.bedarfsermittlungDurchfuehren(ka, false);
//	}
}
