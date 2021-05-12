package domain.bestellverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.bestellverwaltung.provided.IBestellService;
import domain.bestellverwaltung.required.IBestellHibernate;
import domain.shared.Auftragstatus;

public class BestellServiceImpl implements IBestellService {

	private IBestellHibernate hib;
	
	@Inject
	public BestellServiceImpl(IBestellHibernate h) {
		hib = h;
	}

	@Override
	public boolean bestellungFreigeben(Bestellung b) {
		//tu was
		b.setStatus(Auftragstatus.freigegeben);
		return hib.changeBestellung(b);
	}

	@Override
	public boolean bestellungStornieren(Bestellung b) {
		//tu was
				b.setStatus(Auftragstatus.storniert);
				return hib.changeBestellung(b);
	}

	@Override
	public boolean changeBestellung(Bestellung b) {
		return hib.changeBestellung(b);
	}

	@Override
	public boolean createBestellung(Bestellung b) {
		b.setStatus(Auftragstatus.angelegt);
		return hib.createBestellung(b);
	}

	@Override
	public boolean deleteBestellung(Bestellung b) {
		return hib.deleteBestellung(b);
	}

	@Override
	public List<Bestellung> getBestellung(Bestellung b) {
		return hib.getBestellung(b);
	}

	@Override
	public List<Bestellung> getBestellung(Bestellung b, Auftragstatus a) {
		b.setStatus(a);
		return hib.getBestellung(b);
	}
	
	
	
}
