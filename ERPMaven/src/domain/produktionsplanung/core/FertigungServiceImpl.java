package domain.produktionsplanung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.produktionsplanung.provided.IFertigungsService;
import domain.produktionsplanung.required.IFertigungHibernate;
import domain.shared.Auftragstatus;

public class FertigungServiceImpl implements IFertigungsService{

	private IFertigungHibernate hib;
	
	@Inject
	public FertigungServiceImpl(IFertigungHibernate h) {
		hib = h;
	}

	@Override
	public boolean createProduktionsauftrag(Produktionsauftrag f) {
		f.setStatus(Auftragstatus.angelegt);
		return hib.createProduktionsauftrag(f);
	}

	@Override
	public boolean deleteProduktionsauftrag(Produktionsauftrag f) {
		return hib.deleteProduktionsauftrag(f);
	}

	@Override
	public boolean changeProduktionsauftrag(Produktionsauftrag f) {
		return hib.changeProduktionsauftrag(f);
	}

	@Override
	public List<Produktionsauftrag> getProduktionsauftrag(Produktionsauftrag f) {
		return hib.getProduktionsauftrag(f);
	}

	@Override
	public List<Produktionsauftrag> getProduktionsauftrag(Produktionsauftrag f, Auftragstatus a) {
		f.setStatus(a);
		return hib.getProduktionsauftrag(f);
	}

	@Override
	public boolean produktionsauftragFreigeben(Produktionsauftrag f) {
		//tu was
		f.setStatus(Auftragstatus.freigegeben);
		return hib.changeProduktionsauftrag(f);
	}

	@Override
	public boolean produktionsauftragStornieren(Produktionsauftrag f) {
		//tu was
		f.setStatus(Auftragstatus.storniert);
		return hib.changeProduktionsauftrag(f);
	}
	
}
