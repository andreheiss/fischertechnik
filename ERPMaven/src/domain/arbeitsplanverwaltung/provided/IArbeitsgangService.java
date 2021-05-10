package domain.arbeitsplanverwaltung.provided;

import java.util.List;

import domain.arbeitsplanverwaltung.core.Arbeitsgang;
import domain.arbeitsplanverwaltung.core.Arbeitsplan;

public interface IArbeitsgangService {

		//for storing
		public boolean createArbeitsgang(Arbeitsgang ag);
		
		//for retrieving single and multiple 
		public List<Arbeitsgang> getArbeitsgang(Arbeitsgang ag);
		
		//for changing and delimiting
		public boolean changeArbeitsgang(Arbeitsgang ag);
		
		//for deleting
		public boolean deleteArbeitsgang(Arbeitsgang ag);
	
	
}
