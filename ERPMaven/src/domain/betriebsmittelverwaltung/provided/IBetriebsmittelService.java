package domain.betriebsmittelverwaltung.provided;

import java.util.List;

import domain.betriebsmittelverwaltung.core.Betriebsmittel;
import domain.betriebsmittelverwaltung.core.InvalidBetriebsmittelException;

public interface IBetriebsmittelService {

	//for storing
	public boolean createBetriebsmittel(Betriebsmittel bm) throws InvalidBetriebsmittelException;
	
	//for retrieving single or multiple
	public List<Betriebsmittel> getBetriebsmittel(Betriebsmittel bm);
	
	//for changing and delimiting
	public boolean changeBetriebsmittel(Betriebsmittel bm);
	
	//for deleting
	public boolean deleteBetriebsmittel(Betriebsmittel bm);
	
}
