package domain.arbeitsplanverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.arbeitsplanverwaltung.provided.IArbeitsgangService;
import domain.arbeitsplanverwaltung.required.IArbeitsgangHibernate;

public class ArbeitsgangServiceImpl implements IArbeitsgangService {

	private IArbeitsgangHibernate arbeitsgangHibernate;
	
	@Inject
	public ArbeitsgangServiceImpl(IArbeitsgangHibernate hib) {
		
		arbeitsgangHibernate = hib;
	}

	@Override
	public boolean createArbeitsgang(Arbeitsgang ag) {
		return arbeitsgangHibernate.createArbeitsgang(ag);
	}

	@Override
	public List<Arbeitsgang> getArbeitsgang(Arbeitsgang ag) {
		return arbeitsgangHibernate.getArbeitsgang(ag);
	}

	@Override
	public boolean changeArbeitsgang(Arbeitsgang ag) {
	return arbeitsgangHibernate.changeArbeitsgang(ag);
	}

	@Override
	public boolean deleteArbeitsgang(Arbeitsgang ag) {
		return arbeitsgangHibernate.deleteArbeitsgang(ag);
	}
	
	
}
