package domain.betriebsverwaltung.required;

import java.util.List;
import domain.betriebsverwaltung.core.Werk;

public interface IWerkHibernate {

	public boolean createWerk(Werk w);
	
	public List<Werk> getWerk(Werk w);
	
	public boolean changeWerk(Werk w);
	
	public boolean deleteWerk(Werk w);
	
}
