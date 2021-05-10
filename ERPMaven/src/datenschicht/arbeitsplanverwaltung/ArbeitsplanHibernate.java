package datenschicht.arbeitsplanverwaltung;

import java.util.List;

import domain.arbeitsplanverwaltung.core.Arbeitsplan;
import domain.arbeitsplanverwaltung.core.Teil;
import domain.arbeitsplanverwaltung.required.IArbeitsplanHibernate;

public class ArbeitsplanHibernate implements IArbeitsplanHibernate {

	@Override
	public List<Arbeitsplan> getArbeitsplan(Arbeitsplan ap) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Arbeitsplan> getArbeitsplanForTeil(int t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createArbeitsplan(Arbeitsplan ap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeArbeitsplan(Arbeitsplan ap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteArbeitsplan(Arbeitsplan ap) {
		// TODO Auto-generated method stub
		return false;
	}

}
