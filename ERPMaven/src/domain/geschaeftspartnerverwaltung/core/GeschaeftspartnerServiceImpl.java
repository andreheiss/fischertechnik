package domain.geschaeftspartnerverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.geschaeftspartnerverwaltung.provided.IGeschaeftspartnerService;
import domain.geschaeftspartnerverwaltung.required.IGeschaeftspartnerHibernate;

public class GeschaeftspartnerServiceImpl implements IGeschaeftspartnerService {

	private IGeschaeftspartnerHibernate hib;
	
	@Inject
	public GeschaeftspartnerServiceImpl(IGeschaeftspartnerHibernate h) {
		hib = h;
	}

	@Override
	public boolean createGeschaeftspartner(Geschaeftspartner gs) throws InvalidGeschaeftspartnerException{
		return hib.createGeschaeftspartner(gs);
	}

	@Override
	public List<Geschaeftspartner> getGeschaeftspartner(Geschaeftspartner gs) {
		return hib.getGeschaeftspartner(gs);
	}

	@Override
	public boolean changeGeschaeftspartner(Geschaeftspartner gs) {
		return hib.changeGeschaeftspartner(gs);
	}

	@Override
	public boolean deleteGeschaeftspartner(Geschaeftspartner gs) {
		return hib.deleteGeschaeftspartner(gs);
	}
	
	
}
