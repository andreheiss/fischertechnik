package main;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import datenschicht.EntityManagerUtil;
//import domain.bedarfsplanung.core.*;
import domain.bedarfsplanung.provided.IBedarfsermittlungService;
import domain.lagerverwaltung.core.LagerServiceImpl;
import domain.teileverwaltung.core.Teil;
import domain.teileverwaltung.core.TeilServiceImpl;
import main.modules.*;
import domain.lagerverwaltung.core.Lagerplatz;

public class TestBedarfsplanung {

	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new TeilModul(), new ClockModul(), new BetriebsmittelModul(),
				new EntityManagerModul(), new LagerModul(), new ArbeitsplanModul(), new MqttLagerModul(),
				new KundenauftragModul(), new BetriebsverwaltungModul());

		EntityManagerUtil emu = injector.getInstance(EntityManagerUtil.class);

		// <TEST BEDARFSPLANUNG>

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
//		ka.setLieferdatum(LocalDateTime.of(2021, 5, 10, 16, 0));
//		List<Kundenauftragsposition> kpos = new LinkedList<>();
//		kpos.add(kp);
//		ka.setKundenauftragspositionen(kpos);
//
//		IBedarfsermittlungService ibs = injector.getInstance(IBedarfsermittlungService.class);
//		ibs.bedarfErmitteln(1, false);
		// <ENDE TEST BEDARFSPLANUNG>

		// Test Lager <START>

				LagerServiceImpl ls = injector.getInstance(LagerServiceImpl.class);
				TeilServiceImpl ts = injector.getInstance(TeilServiceImpl.class);
				//lagerplatzsuche vorbereiten
				Lagerplatz lp = new Lagerplatz();
				lp.setPosX(1);
				lp.setPosY(1);
				//lagerplatz holen
				lp = ls.getLagerplatz(lp).get(0);
				
				//teilsuche vorbereiten
				Teil t = new Teil();
				t.setBezeichnung("test2");
				//teil holen
				t = ts.getTeil(t).get(0);
				
				lp.setTeilenummer(t.getTeilenummer());
				ls.einlagern(lp);
			System.out.println(ls.getLagerbestand(lp.getTeilenummer()));
			//	ls.auslagern(lp);
				
		// Test Lager <ENDE>

	}

}
