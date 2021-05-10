package domain.betriebsmittelverwaltung.required;

import java.util.List;

import domain.betriebsmittelverwaltung.core.Betriebsmittel;

public interface IBetriebsmittelHibernate {

	//for storing
	public boolean createBetriebsmittel(Betriebsmittel bm);
	
	//for retrieving single or multiple
	public List<Betriebsmittel> getBetriebsmittel(Betriebsmittel bm);
	
	//for changing and delimiting
	public boolean changeBetriebsmittel(Betriebsmittel bm);
	
	//for deleting
	public boolean deleteBetriebsmittel(Betriebsmittel bm);
	
	
}
