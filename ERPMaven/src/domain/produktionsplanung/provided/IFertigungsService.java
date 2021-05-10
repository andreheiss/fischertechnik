package domain.produktionsplanung.provided;

import java.util.List;
import domain.shared.Auftragstatus;
import domain.produktionsplanung.core.Produktionsauftrag;

public interface IFertigungsService {

	
	public boolean createProduktionsauftrag(Produktionsauftrag f);
	
	public boolean deleteProduktionsauftrag(Produktionsauftrag f);
	
	public boolean changeProduktionsauftrag(Produktionsauftrag f);
	
	public List<Produktionsauftrag> getProduktionsauftrag(Produktionsauftrag f);
	
	public List<Produktionsauftrag> getProduktionsauftrag(Produktionsauftrag f, Auftragstatus a);
	
	public boolean produktionsauftragFreigeben(Produktionsauftrag f);
	
	public boolean produktionsauftragStornieren(Produktionsauftrag f);
}
