package domain.bedarfsplanung.core;

import com.google.inject.Inject;

import domain.bedarfsplanung.provided.IBedarfsermittlungService;
import domain.bedarfsplanung.required.IGetKundenauftrag;

public class BedarfsermittlungServiceImpl implements IBedarfsermittlungService{

	private Bedarfsplan bedPlan;
	private IGetKundenauftrag kda;
	
	@Inject
	public BedarfsermittlungServiceImpl(Bedarfsplan bp, IGetKundenauftrag k) {
		bedPlan = bp;
		kda = k;
	}
	
	@Override
	public void bedarfErmitteln(int kdAuftrag, boolean sim) {
		//getKundenAuftrag-Object
		Kundenauftrag ka = kda.getKundenauftrag(kdAuftrag);
		//Object uebergeben
		bedPlan.bedarfsermittlungDurchfuehren(ka, sim);
		
	}

	
	
	
}
