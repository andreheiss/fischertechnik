package domain.arbeitsplanverwaltung.core;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Arbeitsplan {

	private int arbeitsplannummer;
	private Eigenfertigungsteil teil;
	private String bezeichnung;
	private LocalDate gueltigVon;
	private LocalDate gueltigBis;
	private List<Arbeitsgang> arbeitsgaenge;
	private List<Werk> werke;
	
	public Arbeitsplan() {
		arbeitsgaenge = new LinkedList<>();
		werke = new LinkedList<>();
	}

	public int getArbeitsplannummer() {
		return arbeitsplannummer;
	}

	public void setArbeitsplannummer(int arbeitsplannummer) {
		this.arbeitsplannummer = arbeitsplannummer;
	}

	public Eigenfertigungsteil getTeil() {
		return teil;
	}

	public void setTeil(Eigenfertigungsteil teil) {
		this.teil = teil;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public LocalDate getGueltigVon() {
		return gueltigVon;
	}

	public void setGueltigVon(LocalDate gueltigVon) {
		this.gueltigVon = gueltigVon;
	}

	public LocalDate getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(LocalDate gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

	public List<Arbeitsgang> getArbeitsgaenge() {
		return arbeitsgaenge;
	}

	public void setArbeitsgaenge(List<Arbeitsgang> arbeitsgaenge) {
		this.arbeitsgaenge = arbeitsgaenge;
	}

	public List<Werk> getWerke() {
		return werke;
	}

	public void setWerke(List<Werk> werke) {
		this.werke = werke;
	}
	
	void validateArbeitsplan() throws InvalidArbeitsplanException{
		
		if(this.getTeil() == null) {
			throw new InvalidArbeitsplanException("Teil fehlt!");
		}
		
		
		if(this.getBezeichnung() == null) {
			throw new InvalidArbeitsplanException("Bezeichnung fehlt!");
		}
			
		if(this.getGueltigVon() == null) {
			throw new InvalidArbeitsplanException("GueltigVon fehlt!");
		}
		
		if(this.getGueltigBis() == null) {
			throw new InvalidArbeitsplanException("GueltigBis fehlt!");
		}
		if(getGueltigBis().compareTo(getGueltigVon())< 0) {
			throw new InvalidArbeitsplanException("ungueltiger Zeitraum!");
			
		}
		if(this.getWerke() == null || this.getWerke().size() == 0) {
			throw new InvalidArbeitsplanException("Werk fehlt!");
		}
	}
	
	
}
