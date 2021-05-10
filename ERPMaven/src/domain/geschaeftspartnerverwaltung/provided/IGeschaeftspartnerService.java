package domain.geschaeftspartnerverwaltung.provided;

import java.util.List;

import domain.geschaeftspartnerverwaltung.core.Geschaeftspartner;
import domain.geschaeftspartnerverwaltung.core.InvalidGeschaeftspartnerException;


public interface IGeschaeftspartnerService {

	
	public boolean createGeschaeftspartner(Geschaeftspartner gs) throws  InvalidGeschaeftspartnerException;
	
	public List<Geschaeftspartner> getGeschaeftspartner(Geschaeftspartner gs);
	
	public boolean changeGeschaeftspartner(Geschaeftspartner gs);
	
	public boolean deleteGeschaeftspartner(Geschaeftspartner gs);
	
}
