package domain.produktionsplanung.core;

import com.google.inject.Inject;

import domain.artificialClock.IClockService;
import domain.artificialClock.IObserver;

public class Produktionsplaner implements IObserver{

	private IClockService clock;
	
	@Inject
	public Produktionsplaner(IClockService c) {
		clock = c;
		clock.anmelden(this);
	}
	
	@Override
	public void aktualisieren() {
		//jede sekunde durch Uhr aufgerufen
	//	System.out.println(clock.getTime());
		
		//prüfen, ob Auftrag begonnen werden kann
		
		// Sobald Auftrag übergeben, abmelden von Uhr und auf Rückmeldung MQTT warten
		
	}

}
