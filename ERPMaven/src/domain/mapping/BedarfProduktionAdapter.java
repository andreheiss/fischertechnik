package domain.mapping;

import com.google.inject.Inject;

import domain.bedarfsplanung.core.Fertigungsauftrag;
import domain.bedarfsplanung.required.ITerminierung;
import domain.produktionsplanung.core.Fertigungsteil;
import domain.produktionsplanung.core.Produktionsauftrag;
import domain.produktionsplanung.provided.ITerminierService;

public class BedarfProduktionAdapter implements ITerminierung {

	private ITerminierService terminService;

	@Inject
	public BedarfProduktionAdapter(ITerminierService ts) {
		terminService = ts;
	}

	@Override
	public Fertigungsauftrag vorwaertsTerminieren(Fertigungsauftrag fa) {
		Produktionsauftrag pa = auftragumwandeln(fa);
		terminService.vortwaertsTerminieren(pa);
		fa.setEnde(pa.getFertigungsende());
		return fa;
	}

	@Override
	public Fertigungsauftrag rueckwaertsTerminieren(Fertigungsauftrag fa) {
		Produktionsauftrag pa = auftragumwandeln(fa);
		terminService.rueckwaertsTerminieren(pa);
		fa.setStart(pa.getFertigungsstart());
		return fa;
	}
	public Produktionsauftrag auftragumwandeln(Fertigungsauftrag fa) {
		
				Fertigungsteil ft = new Fertigungsteil();
				ft.setTeilenummer(fa.getTeil().getTeilenummer());
				
				Produktionsauftrag pa = new Produktionsauftrag();
				pa.setTeil(ft);
				pa.setAuftragsnummer(fa.getAuftragsnummer());
				pa.setFertigungsstart(fa.getStart());
				pa.setMenge(fa.getMenge());
				// pa.setStatus(null); -> in Fertigungsauftrag nicht gesetzt
				return pa;
	}
	

}
