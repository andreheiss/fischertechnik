package domain.arbeitsplanverwaltung.provided;

import java.util.List;

import domain.arbeitsplanverwaltung.core.Arbeitsgang;
import domain.arbeitsplanverwaltung.core.Arbeitsplan;
import domain.arbeitsplanverwaltung.core.InvalidArbeitsgangException;

public interface IArbeitsgangService {

		//for storing
		public boolean createArbeitsgang(Arbeitsgang ag) throws InvalidArbeitsgangException;
		
		//for retrieving single and multiple 
		public List<Arbeitsgang> getArbeitsgang(Arbeitsgang ag);
		
		//for changing and delimiting
		public boolean changeArbeitsgang(Arbeitsgang ag)throws InvalidArbeitsgangException;
		
		//for deleting
		public boolean deleteArbeitsgang(Arbeitsgang ag);
	
	
}
