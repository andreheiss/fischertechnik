package domain.lagerverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.lagerverwaltung.provided.ILagerService;
import domain.lagerverwaltung.required.ILagerHibernate;


public class LagerServiceImpl implements ILagerService{

	private ILagerHibernate hib;
	
	@Inject
	public LagerServiceImpl(ILagerHibernate h) {
		hib = h;
	}
	

	@Override
	public boolean createLagerplatz(Lagerplatz lp) {
		return hib.createLagerplatz(lp);
	}

	@Override
	public List<Lagerplatz> getLagerplatz(Lagerplatz lp) {
		return hib.getLagerplatz(lp);
	}

	@Override
	public boolean changeLagerplatz(Lagerplatz lp) {
		return hib.changeLagerplatz(lp);
	}

	@Override
	public boolean deleteLagerplatz(Lagerplatz lp) {
		return hib.deleteLagerplatz(lp);
	}

	@Override
	public void einlagern(Lagerplatz lp) {
		//settern teilenummer entfernen schlecht wegen Hibernate
		hib.changeLagerplatz(lp);
	}

	@Override
	public void auslagern(Lagerplatz lp) {
		//settern teilenummer entfernen schlecht wegen Hibernate
		lp.setTeilenummer(null);
		hib.changeLagerplatz(lp);
		
	}
	public int getLagerbestand(int teilenummer) {
		return hib.getLagerbestand(teilenummer);
	}
	

}
