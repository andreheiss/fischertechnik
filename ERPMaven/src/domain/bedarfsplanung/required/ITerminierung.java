package domain.bedarfsplanung.required;

import domain.bedarfsplanung.core.Fertigungsauftrag;

public interface ITerminierung {

	public Fertigungsauftrag vorwaertsTerminieren(Fertigungsauftrag fa);
	public Fertigungsauftrag rueckwaertsTerminieren(Fertigungsauftrag fa);
	
}
