package domain.produktionsplanung.required;

import java.util.List;

import domain.produktionsplanung.core.Produktionsauftrag;

public interface IFertigungHibernate {

	public boolean createProduktionsauftrag(Produktionsauftrag f);
	
	public boolean deleteProduktionsauftrag(Produktionsauftrag f);
	
	public boolean changeProduktionsauftrag(Produktionsauftrag f);
	
	public List<Produktionsauftrag> getProduktionsauftrag(Produktionsauftrag f);
	
}
