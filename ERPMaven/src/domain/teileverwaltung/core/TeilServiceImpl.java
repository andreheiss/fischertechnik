package domain.teileverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.teileverwaltung.providedservices.ITeilService;
import domain.teileverwaltung.requiredservices.ITeilHibernate;

public class TeilServiceImpl implements ITeilService{
	

	private ITeilHibernate teilHibernate;
	
	@Inject
	public TeilServiceImpl(ITeilHibernate repo) {
		teilHibernate = repo;
	}

	@Override
	public boolean createTeil(Teil t) throws InvalidTeilException {
		t.validateTeil();
		return teilHibernate.createTeil(t);
	}

	@Override
	public List<Teil> getTeil(Teil t) {
		return teilHibernate.getTeil(t);
	}

	@Override
	public boolean changeTeil(Teil t) throws InvalidTeilException{
		t.validateTeil();
		return teilHibernate.changeTeil(t);
	}

	@Override
	public boolean deleteTeil(Teil t) {
		return teilHibernate.deleteTeil(t);
	}

	


	
	
}
