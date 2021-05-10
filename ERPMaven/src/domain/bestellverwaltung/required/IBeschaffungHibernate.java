package domain.bestellverwaltung.required;

import java.util.List;

import domain.bestellverwaltung.core.Beschaffungsauftrag;

public interface IBeschaffungHibernate {

	
	public boolean changeBeschaffungsauftrag(Beschaffungsauftrag b);
	
	public boolean createBeschaffungsauftrag(Beschaffungsauftrag b);
	
	public boolean deleteBeschaffungsauftrag(Beschaffungsauftrag b);
	
	public List<Beschaffungsauftrag> getBeschaffungsauftrag(Beschaffungsauftrag b);
	
}
