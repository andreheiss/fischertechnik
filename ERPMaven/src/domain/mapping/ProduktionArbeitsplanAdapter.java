package domain.mapping;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import domain.arbeitsplanverwaltung.core.Arbeitsgang;
import domain.arbeitsplanverwaltung.core.Arbeitsplan;
import domain.arbeitsplanverwaltung.provided.IArbeitsplanService;
import domain.produktionsplanung.core.Fertigungsarbeitsgang;
import domain.produktionsplanung.core.Fertigungsarbeitsplan;
import domain.produktionsplanung.core.Fertigungsteil;
import domain.produktionsplanung.required.IGetArbeitsplan;

public class ProduktionArbeitsplanAdapter implements IGetArbeitsplan{

	private IArbeitsplanService ias;
	
	@Inject
	public ProduktionArbeitsplanAdapter(IArbeitsplanService ias) {
		this.ias = ias;
	}

	@Override
	public Fertigungsarbeitsplan getArbeitsplanForTeil(int id) {
		
		Fertigungsarbeitsplan fap = new Fertigungsarbeitsplan();
		List<Arbeitsplan> apl = ias.getArbeitsplanForTeil(id);
		if(apl.size() != 1) {
			throw new RuntimeException("mehrere oder kein Plan gefunden!");
		}
		Arbeitsplan ap = apl.get(0);
		
		//fap.setArbeitsplannummer(ap.getArbeitsplannummer()); --> eigene Nummer sinnvoll! durch DB beim speichern erstellen lassen
		Fertigungsteil ft = new Fertigungsteil();
		ft.setTeilenummer(ap.getFertigungsteilenummer());
		fap.setTeil(ft);
		List<Fertigungsarbeitsgang> agl = new LinkedList<>();
		Fertigungsarbeitsgang fag;
		for(Arbeitsgang ag : ap.getArbeitsgaenge()) {
			fag = new Fertigungsarbeitsgang();
			fag.setArbeitsgangnummer(ag.getArbeitsgangnummer());
			fag.setBearbeitungszeit(ag.getBearbeitungszeit());
			fag.setRuestzeit(ag.getRuestzeit());
			//Betriebsmittel noch anhaengen -> evtl nur ein Betriebsmittel pro Arbeitsgang
			//-> evtl umbenennen zu Fertigungsbetriebsmittel
			agl.add(fag);
		}
		fap.setArbeitsgaenge(agl);
		
		
		return fap;
	}
	
	
	
}
