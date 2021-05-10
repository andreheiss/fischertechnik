package domain.lagerverwaltung.provided;

import java.util.List;

import domain.lagerverwaltung.core.Lagerplatz;


public interface ILagerService {

	public boolean createLagerplatz(Lagerplatz lp);

	public List<Lagerplatz> getLagerplatz(Lagerplatz lp);

	public boolean changeLagerplatz(Lagerplatz lp);

	public boolean deleteLagerplatz(Lagerplatz lp);

	public void einlagern(Lagerplatz lp); 

	public void auslagern(Lagerplatz lp);
	
	public int getLagerbestand(int teilenummer);

	
}
