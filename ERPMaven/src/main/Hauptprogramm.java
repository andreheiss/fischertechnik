package main;

import com.google.inject.Guice;
import com.google.inject.Injector;

import datenschicht.EntityManagerUtil;
import domain.artificialClock.ClockService;
import domain.lagerverwaltung.core.LagerServiceImpl;
import domain.lagerverwaltung.core.Lagerplatz;
import domain.lagerverwaltung.core.Lagerteil;
import domain.mqtt.core._MQTT_Lernfabrik;
import domain.produktionsplanung.core.Produktionsplaner;
import domain.teileverwaltung.core.Teil;
import domain.teileverwaltung.core.TeilServiceImpl;
import main.modules.*;
import gui.Main;

public class Hauptprogramm {

	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new TeilModul(), new ClockModul(),new BetriebsmittelModul(),
												 new EntityManagerModul(),new LagerModul(),new ArbeitsplanModul(),
												 new MqttLagerModul(), new KundenauftragModul(), new BetriebsverwaltungModul());			
							
							
		//, new BedarfsermittlungServiceModul(), new BedarfsProduktionModul(), new ProduktionArbeitsplanModul(), new BedarfKundenauftragModul(),new ProduktionplanungServiceModul());	
		 

		ClockService clock = injector.getInstance(ClockService.class);
	//	Produktionsplaner p = injector.getInstance(Produktionsplaner.class);
		EntityManagerUtil emu = injector.getInstance(EntityManagerUtil.class);
		_MQTT_Lernfabrik mqtt = injector.getInstance(_MQTT_Lernfabrik.class);
	//	mqtt.launch();
		
		Main.startGui(injector);
	
	
		
		
		
		
		
		
	}

}
