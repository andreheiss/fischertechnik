package domain.produktionsplanung.core;

import java.time.LocalDate;
import java.util.List;

public class Fertigungsarbeitsplan {

	private int arbeitsplannummer;
	private Fertigungsteil teil;
	private List<Fertigungsarbeitsgang> arbeitsgaenge;
	
	public int getArbeitsplannummer() {
		return arbeitsplannummer;
	}
	public void setArbeitsplannummer(int arbeitsplannummer) {
		this.arbeitsplannummer = arbeitsplannummer;
	}
	public Fertigungsteil getTeil() {
		return teil;
	}
	public void setTeil(Fertigungsteil teil) {
		this.teil = teil;
	}
	public List<Fertigungsarbeitsgang> getArbeitsgaenge() {
		return arbeitsgaenge;
	}
	public void setArbeitsgaenge(List<Fertigungsarbeitsgang> arbeitsgaenge) {
		this.arbeitsgaenge = arbeitsgaenge;
	}
	
	
}
