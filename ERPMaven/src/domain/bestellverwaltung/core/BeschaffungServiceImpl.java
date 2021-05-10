package domain.bestellverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.bestellverwaltung.provided.IBeschaffungService;
import domain.bestellverwaltung.required.IBeschaffungHibernate;
import domain.shared.Auftragstatus;

public class BeschaffungServiceImpl implements IBeschaffungService {

	private IBeschaffungHibernate hib;
	
	@Inject
	public BeschaffungServiceImpl(IBeschaffungHibernate h) {
		hib = h;
	}

	@Override
	public boolean beschaffungsauftragFreigeben(Beschaffungsauftrag b) {
		//tu was
		b.setStatus(Auftragstatus.freigegeben);
		return hib.changeBeschaffungsauftrag(b);
	}

	@Override
	public boolean beschaffungsauftragStornieren(Beschaffungsauftrag b) {
		//tu was
				b.setStatus(Auftragstatus.storniert);
				return hib.changeBeschaffungsauftrag(b);
	}

	@Override
	public boolean changeBeschaffungsauftrag(Beschaffungsauftrag b) {
		return hib.changeBeschaffungsauftrag(b);
	}

	@Override
	public boolean createBeschaffungsauftrag(Beschaffungsauftrag b) {
		b.setStatus(Auftragstatus.angelegt);
		return hib.createBeschaffungsauftrag(b);
	}

	@Override
	public boolean deleteBeschaffungsauftrag(Beschaffungsauftrag b) {
		return hib.deleteBeschaffungsauftrag(b);
	}

	@Override
	public List<Beschaffungsauftrag> getBeschaffungsauftrag(Beschaffungsauftrag b) {
		return hib.getBeschaffungsauftrag(b);
	}

	@Override
	public List<Beschaffungsauftrag> getBeschaffungsauftrag(Beschaffungsauftrag b, Auftragstatus a) {
		b.setStatus(a);
		return hib.getBeschaffungsauftrag(b);
	}
	
	
	
}
