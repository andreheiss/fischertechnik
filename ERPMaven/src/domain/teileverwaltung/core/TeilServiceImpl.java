package domain.teileverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import domain.teileverwaltung.providedservices.ITeilService;
import domain.teileverwaltung.requiredservices.ITeilHibernate;

public class TeilServiceImpl implements ITeilService {

	private ITeilHibernate teilHibernate;

	@Inject
	public TeilServiceImpl(ITeilHibernate repo) {
		teilHibernate = repo;
	}

	@Override
	public boolean createTeil(Teil t) throws InvalidTeilException {
		this.checkIfChild(t);
		t.validateTeil();
		return teilHibernate.createTeil(t);
	}

	@Override
	public List<Teil> getTeil(Teil t) {
		return teilHibernate.getTeil(t);
	}

	@Override
	public boolean changeTeil(Teil t) throws InvalidTeilException {
		this.checkIfChild(t);
		t.validateTeil();
		return teilHibernate.changeTeil(t);
	}

	@Override
	public boolean deleteTeil(Teil t) {
		return teilHibernate.deleteTeil(t);
	}
	
	//verhindern, dass nur Teil angelegt wird, statt Kindklassen!
	private void checkIfChild(Teil t) throws InvalidTeilException {
		if(! (t instanceof Eigenfertigungsteil || t instanceof Fremdbezugsteil)) {
			throw new InvalidTeilException("Teil muss Fremd- oder Eigenfertigungsteil sein!");
		}
	

	}
	
	
}
