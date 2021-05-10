package domain.betriebsverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.betriebsverwaltung.provided.IWerkService;
import domain.betriebsverwaltung.required.IWerkHibernate;

public class WerkServiceImpl implements IWerkService{

	private IWerkHibernate hib;
	
	@Inject
	public WerkServiceImpl(IWerkHibernate h) {
		hib = h;
	}

	@Override
	public boolean createWerk(Werk w) {
		return hib.createWerk(w);
	}

	@Override
	public List<Werk> getWerk(Werk w) {
		return hib.getWerk(w);
	}

	@Override
	public boolean changeWerk(Werk w) {
		return hib.changeWerk(w);
	}

	@Override
	public boolean deleteWerk(Werk w) {
		return hib.deleteWerk(w);
	}
	
	
}
