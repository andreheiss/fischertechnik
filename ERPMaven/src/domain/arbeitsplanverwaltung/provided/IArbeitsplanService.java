package domain.arbeitsplanverwaltung.provided;

import java.util.List;

import domain.arbeitsplanverwaltung.core.Arbeitsplan;

public interface IArbeitsplanService {

	//for retrieving single and multiple
		public List<Arbeitsplan> getArbeitsplan(Arbeitsplan ap);
		
		public List<Arbeitsplan> getArbeitsplanForTeil(int t);
		
		//for storing
		public boolean createArbeitsplan(Arbeitsplan ap);
		
		//for changing
		public boolean changeArbeitsplan(Arbeitsplan ap);
		
		//for deleting
		public boolean deleteArbeitsplan(Arbeitsplan ap);
	
}
