package domain.arbeitsplanverwaltung.core;

import java.util.List;

import com.google.inject.Inject;

import datenschicht.arbeitsplanverwaltung.ArbeitsplanHibernate;
import domain.arbeitsplanverwaltung.provided.IArbeitsplanService;
import domain.arbeitsplanverwaltung.required.IArbeitsplanHibernate;

public class ArbeitsplanServiceImpl implements IArbeitsplanService{

	private IArbeitsplanHibernate arbeitsplanHibernate;
	
	@Inject
	public ArbeitsplanServiceImpl(IArbeitsplanHibernate hib) {
		arbeitsplanHibernate = hib;
	}

	@Override
	public List<Arbeitsplan> getArbeitsplan(Arbeitsplan ap) {
		return arbeitsplanHibernate.getArbeitsplan(ap);
	}
	
	@Override
	public List<Arbeitsplan> getArbeitsplanForTeil(int t){
		return arbeitsplanHibernate.getArbeitsplanForTeil(t);
	}

	@Override
	public boolean createArbeitsplan(Arbeitsplan ap) throws InvalidArbeitsplanException{
		ap.validateArbeitsplan();
		return arbeitsplanHibernate.createArbeitsplan(ap);
	}

	@Override
	public boolean changeArbeitsplan(Arbeitsplan ap) {
		return arbeitsplanHibernate.changeArbeitsplan(ap);
	}

	@Override
	public boolean deleteArbeitsplan(Arbeitsplan ap) {
		return arbeitsplanHibernate.deleteArbeitsplan(ap);
	}
}
