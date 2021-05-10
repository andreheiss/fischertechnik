package domain.mapping;

import com.google.inject.Inject;

import domain.bedarfsplanung.core.Kundenauftrag;
import domain.bedarfsplanung.core.Kundenauftragsposition;
import domain.bedarfsplanung.core.Teil;
import domain.bedarfsplanung.required.IGetKundenauftrag;
import domain.kundenauftragsverwaltung.provided.IKundenauftragService;
import domain.lagerverwaltung.provided.ILagerService;
import domain.teileverwaltung.providedservices.ITeilService;

public class BedarfKundenauftragAdapter implements IGetKundenauftrag {

	private IKundenauftragService kas;
	private ITeilService ts;
	private ILagerService ls;
	
	@Inject
	public BedarfKundenauftragAdapter(IKundenauftragService k) {
		kas = k;
	}
	
	@Override
	public Kundenauftrag getKundenauftrag(int kdauftrnummer) {
		
		domain.kundenauftragsverwaltung.core.Kundenauftrag kvKundenauf = new domain.kundenauftragsverwaltung.core.Kundenauftrag(); //nicht schoen, aber selten :)
		kvKundenauf.setKundenauftragsnummer(kdauftrnummer);
		kvKundenauf = kas.getKundenauftrag(kvKundenauf).get(0); //Fehlerbehandlung falls null oder mehrere
		
		Kundenauftrag bpKundenauf = new Kundenauftrag();
		bpKundenauf.setKundenauftragsnummer(kvKundenauf.getKundenauftragsnummer());
		bpKundenauf.setLieferdatum(kvKundenauf.getLieferzeitpunkt());
		
		Kundenauftragsposition kdpos;
		//Kundenauftragspositionen uebertragen
		for(domain.kundenauftragsverwaltung.core.Kundenauftragsposition kp: kvKundenauf.getPositionen()) {
			kdpos = new Kundenauftragsposition();
			
			kdpos.setKundenauftragspositionsnummer(kp.getPositionsnummer());
			kdpos.setMenge(kp.getMenge());
			
			domain.teileverwaltung.core.Teil teil = new domain.teileverwaltung.core.Teil();
			teil.setTeilenummer(kp.getTeilenummer());
			teil = ts.getTeil(teil).get(0); //Fehlerbehandlung
			
			//Eigenes Hibernate-Mapping fuer Teil machen, zu umstaendlich!!!
			
			//Reservierungen auch noch reinladen
			
			//kdpos.setTeil(t);
			
			
			
		}
		
		return bpKundenauf;
		
		
	}

}
