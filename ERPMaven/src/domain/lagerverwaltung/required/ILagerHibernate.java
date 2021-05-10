package domain.lagerverwaltung.required;

import java.util.List;

import domain.lagerverwaltung.core.Lagerplatz;

public interface ILagerHibernate {
	
	public boolean createLagerplatz(Lagerplatz lp);
	
	public boolean changeLagerplatz(Lagerplatz lp);

	public boolean deleteLagerplatz(Lagerplatz lp);

	public List<Lagerplatz> getLagerplatz(Lagerplatz lp);
	
}
