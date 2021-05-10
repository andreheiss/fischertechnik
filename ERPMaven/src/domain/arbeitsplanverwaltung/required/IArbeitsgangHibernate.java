package domain.arbeitsplanverwaltung.required;

import java.util.List;

import domain.arbeitsplanverwaltung.core.Arbeitsgang;
import domain.arbeitsplanverwaltung.core.Arbeitsplan;

//notwendig damit Arbeitsgang ohne direkte Zuordnung zu Arbeitsplan erstellt werden kann!
public interface IArbeitsgangHibernate {

	//for storing
	public boolean createArbeitsgang(Arbeitsgang ag);
	
	//for retrieving single and multiple 
	public List<Arbeitsgang> getArbeitsgang(Arbeitsgang ag);
	
	//for changing and delimiting
	public boolean changeArbeitsgang(Arbeitsgang ag);
	
	//for deleting
	public boolean deleteArbeitsgang(Arbeitsgang ag);
	
	
	
	
}
