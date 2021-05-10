package domain.bedarfsplanung.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Auftrag {

	private int auftragsnummer;
	private int menge;
	private LocalDateTime start;
	private LocalDateTime ende;
	//status hier nicht relevant

	public int getAuftragsnummer() {
		return auftragsnummer;
	}

	public void setAuftragsnummer(int auftragsnummer) {
		this.auftragsnummer = auftragsnummer;
	}
	
	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnde() {
		return ende;
	}

	public void setEnde(LocalDateTime ende) {
		this.ende = ende;
	}
	public long getDauer() {
		return this.getStart().until(this.getEnde(), ChronoUnit.SECONDS);
	}
	
}
