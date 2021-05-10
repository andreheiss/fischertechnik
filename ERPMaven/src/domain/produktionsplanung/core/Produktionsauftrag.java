package domain.produktionsplanung.core;

import java.time.LocalDateTime;

import domain.shared.Auftragstatus;

public class Produktionsauftrag{

	private int auftragsnummer;
	private Fertigungsteil teil;
	private int menge;
	private LocalDateTime fertigungsstart;
	private LocalDateTime fertigungsende;
	private Auftragstatus status;
	
	
	public int getAuftragsnummer() {
		return auftragsnummer;
	}
	public void setAuftragsnummer(int auftragsnummer) {
		this.auftragsnummer = auftragsnummer;
	}
	public Fertigungsteil getTeil() {
		return teil;
	}
	public void setTeil(Fertigungsteil teil) {
		this.teil = teil;
	}
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	
	public LocalDateTime getFertigungsstart() {
		return fertigungsstart;
	}
	public void setFertigungsstart(LocalDateTime fertigungsstart) {
		this.fertigungsstart = fertigungsstart;
	}
	public LocalDateTime getFertigungsende() {
		return fertigungsende;
	}
	public void setFertigungsende(LocalDateTime fertigungsende) {
		this.fertigungsende = fertigungsende;
	}
	public Auftragstatus getStatus() {
		return status;
	}
	public void setStatus(Auftragstatus status) {
		this.status = status;
	}
	
	
	
	
	
	
}
