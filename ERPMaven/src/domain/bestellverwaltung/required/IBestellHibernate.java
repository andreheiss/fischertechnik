package domain.bestellverwaltung.required;

import java.util.List;

import domain.bestellverwaltung.core.Bestellung;

public interface IBestellHibernate {

	
	public boolean changeBestellung(Bestellung b);
	
	public boolean createBestellung(Bestellung b);
	
	public boolean deleteBestellung(Bestellung b);
	
	public List<Bestellung> getBestellung(Bestellung b);
	
}
