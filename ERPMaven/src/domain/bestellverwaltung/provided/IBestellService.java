package domain.bestellverwaltung.provided;

import java.util.List;
import domain.shared.Auftragstatus;
import domain.bestellverwaltung.core.Bestellung;

public interface IBestellService {

	public boolean bestellungFreigeben(Bestellung b);
	
	public boolean bestellungStornieren(Bestellung b);
	
	public boolean changeBestellung(Bestellung b);
	
	public boolean createBestellung(Bestellung b);
	
	public boolean deleteBestellung(Bestellung b);
	
	public List<Bestellung> getBestellung(Bestellung b);
	
	public List<Bestellung> getBestellung(Bestellung b, Auftragstatus a);
	
}
