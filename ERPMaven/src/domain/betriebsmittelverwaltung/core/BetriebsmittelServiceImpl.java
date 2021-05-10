package domain.betriebsmittelverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.betriebsmittelverwaltung.provided.IBetriebsmittelService;
import domain.betriebsmittelverwaltung.required.IBetriebsmittelHibernate;

public class BetriebsmittelServiceImpl implements IBetriebsmittelService {

	private IBetriebsmittelHibernate betriebsmittelHibernate;
	
	@Inject
	public BetriebsmittelServiceImpl(IBetriebsmittelHibernate hib) {
		
		betriebsmittelHibernate = hib;
	}

	@Override
	public boolean createBetriebsmittel(Betriebsmittel bm) throws InvalidBetriebsmittelException {
		bm.validateBetriebsmittel();
		return betriebsmittelHibernate.createBetriebsmittel(bm);
	}

	@Override
	public List<Betriebsmittel> getBetriebsmittel(Betriebsmittel bm) {
		return betriebsmittelHibernate.getBetriebsmittel(bm);
	}

	@Override
	public boolean changeBetriebsmittel(Betriebsmittel bm) {
		return betriebsmittelHibernate.changeBetriebsmittel(bm);
	}

	@Override
	public boolean deleteBetriebsmittel(Betriebsmittel bm) {
		return betriebsmittelHibernate.deleteBetriebsmittel(bm);
	}
	
}
