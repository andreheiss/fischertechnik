package domain.teileverwaltung.requiredservices;

import java.util.List;

import domain.teileverwaltung.core.Teil;

public interface ITeilHibernate {
	
		//for storing
		
		public boolean createTeil(Teil t);
	
		//for retrieving single or multiple
		public List<Teil> getTeil(Teil t);
		
		//for changing and delimiting
		public boolean changeTeil(Teil t);
		
		//for deleting
		public boolean deleteTeil(Teil t);
	
}
