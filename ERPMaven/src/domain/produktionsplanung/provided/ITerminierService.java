package domain.produktionsplanung.provided;

import domain.produktionsplanung.core.Produktionsauftrag;

public interface ITerminierService {

	public Produktionsauftrag vortwaertsTerminieren(Produktionsauftrag fa);
	
	public Produktionsauftrag rueckwaertsTerminieren(Produktionsauftrag fa);
	
}
