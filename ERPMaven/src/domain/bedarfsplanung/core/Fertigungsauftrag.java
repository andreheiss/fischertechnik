package domain.bedarfsplanung.core;

import java.time.LocalDate;
import java.util.List;

public class Fertigungsauftrag extends Auftrag{

	private Eigenfertigungsteil teil;
	private List<Bruttobedarf> sekundaerbedarfe;
	
	public Eigenfertigungsteil getTeil() {
		return teil;
	}
	public void setTeil(Eigenfertigungsteil teil) {
		this.teil = teil;
	}
		
	public List<Bruttobedarf> getSekundaerbedarfe() {
		return sekundaerbedarfe;
	}
	public void setSekundaerbedarfe(List<Bruttobedarf> sekundaerbedarfe) {
		this.sekundaerbedarfe = sekundaerbedarfe;
	}
	
}
