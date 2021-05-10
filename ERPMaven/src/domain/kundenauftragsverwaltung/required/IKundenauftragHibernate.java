package domain.kundenauftragsverwaltung.required;

import java.util.List;

import domain.kundenauftragsverwaltung.core.Kundenauftrag;

public interface IKundenauftragHibernate {

	public boolean createKundenauftrag(Kundenauftrag ka);
	
	public List<Kundenauftrag> getKundenauftrag(Kundenauftrag ka);
	
	public boolean changeKundenauftrag(Kundenauftrag ka);
	
	public boolean deleteKundenauftrag(Kundenauftrag ka);
	
	
}
