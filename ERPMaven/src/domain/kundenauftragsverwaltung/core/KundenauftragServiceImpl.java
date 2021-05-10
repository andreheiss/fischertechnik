package domain.kundenauftragsverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.kundenauftragsverwaltung.provided.IKundenauftragService;
import domain.kundenauftragsverwaltung.required.IBedarfsermittlung;
import domain.kundenauftragsverwaltung.required.IKundenauftragHibernate;
import domain.shared.Auftragstatus;

public class KundenauftragServiceImpl implements IKundenauftragService{

	private IKundenauftragHibernate hib;
	private IBedarfsermittlung bedarf;
	
	@Inject
	public KundenauftragServiceImpl(IKundenauftragHibernate h) {
		hib = h;
	}
	
	@Override
	public boolean createKundenauftrag(Kundenauftrag ka) {
		ka.setStatus(Auftragstatus.angelegt);
		//erst in db anlegen, dann bedarfsermittlung durchfuehren
		if( hib.createKundenauftrag(ka)) {
			bedarf.bedarfsermittlungDurchfuehren(ka, true);
			return true;
		}
		return false;
	}

	@Override
	public List<Kundenauftrag> getKundenauftrag(Kundenauftrag ka) {
		return hib.getKundenauftrag(ka);
	}
	@Override
	public List<Kundenauftrag> getKundenauftrag(Kundenauftrag ka, Auftragstatus a) {
		ka.setStatus(a);
		return hib.getKundenauftrag(ka);
	}
	
	@Override
	public boolean changeKundenauftrag(Kundenauftrag ka) {
		return hib.changeKundenauftrag(ka);
	}

	@Override
	public boolean deleteKundenauftrag(Kundenauftrag ka) {
		return hib.deleteKundenauftrag(ka);
	}

	@Override
	public boolean kundenauftragFreigeben(Kundenauftrag ka) {
		// tu was
		ka.setStatus(Auftragstatus.freigegeben);
		return this.changeKundenauftrag(ka);
	}
	public boolean kundenauftragStornieren(Kundenauftrag ka) {
		// tu was
		ka.setStatus(Auftragstatus.storniert);
		return this.changeKundenauftrag(ka);
	}

	

}
