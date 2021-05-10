package domain.bestellverwaltung.provided;

import java.util.List;
import domain.shared.Auftragstatus;
import domain.bestellverwaltung.core.Beschaffungsauftrag;

public interface IBeschaffungService {

	public boolean beschaffungsauftragFreigeben(Beschaffungsauftrag b);
	
	public boolean beschaffungsauftragStornieren(Beschaffungsauftrag b);
	
	public boolean changeBeschaffungsauftrag(Beschaffungsauftrag b);
	
	public boolean createBeschaffungsauftrag(Beschaffungsauftrag b);
	
	public boolean deleteBeschaffungsauftrag(Beschaffungsauftrag b);
	
	public List<Beschaffungsauftrag> getBeschaffungsauftrag(Beschaffungsauftrag b);
	
	public List<Beschaffungsauftrag> getBeschaffungsauftrag(Beschaffungsauftrag b, Auftragstatus a);
	
}
