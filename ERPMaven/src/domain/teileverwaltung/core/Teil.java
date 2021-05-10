package domain.teileverwaltung.core;


import java.time.LocalDate;

import domain.shared.Farbe;
import domain.shared.Mengeneinheit;
import domain.shared.Planungsart;
import domain.shared.Teileart;

public class Teil {
	
	
	private int teilenummer;
	private LocalDate gueltigVon;
	private LocalDate gueltigBis;
	private String bezeichnung;
	private Mengeneinheit mengeneinheit;
	private Teileart teileart;
	private Planungsart planart;
	private Farbe farbe;

	
	public int getTeilenummer() {
		return teilenummer;
	}
	public void setTeilenummer(int teilenummer) {
		this.teilenummer = teilenummer;
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
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public Mengeneinheit getMengeneinheit() {
		return mengeneinheit;
	}
	public void setMengeneinheit(Mengeneinheit mengeneinheit) {
		this.mengeneinheit = mengeneinheit;
	}
	public Teileart getTeileart() {
		return teileart;
	}
	public void setTeileart(Teileart teileart) {
		this.teileart = teileart;
	}
	public Planungsart getPlanart() {
		return planart;
	}
	public void setPlanart(Planungsart planart) {
		this.planart = planart;
	}
	public Farbe getFarbe() {
		return farbe;
	}
	public void setFarbe(Farbe farbe) {
		this.farbe = farbe;
	}
	@Override
	public String toString() {
		return "" + teilenummer + ", " + gueltigVon + ", " + gueltigBis
				+ ", " + bezeichnung + ", " + mengeneinheit + ", " + teileart
				+ ", " + planart + ", " + farbe;
	}

	void validateTeil() throws InvalidTeilException{
		if(this.getBezeichnung() == null) {
			throw new InvalidTeilException("Bezeichnung fehlt!");
		}
		if(this.getMengeneinheit() == null) {
			throw new InvalidTeilException("Mengeneinheit fehlt!");
		}
		if(this.getPlanart() == null) {
			throw new InvalidTeilException("Planungart fehlt!");
		}
		//Teileart eher optional
//		if(this.getTeileart() == null) {
//			throw new InvalidTeilException("keine Teileart gesetzt!");
//		}
		if(this.getFarbe() == null) {
			throw new InvalidTeilException("Farbe fehlt!");
		}
		if(this.getGueltigVon() == null) {
			throw new InvalidTeilException("GueltigVon fehlt!");
		}
		
		if(this.getGueltigBis() == null) {
			throw new InvalidTeilException("GueltigBis fehlt!");
		}
		if(getGueltigBis().compareTo(getGueltigVon())< 0) {
			throw new InvalidTeilException("ungueltiger Zeitraum!");
			
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + teilenummer;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teil other = (Teil) obj;
		return (teilenummer != other.teilenummer);
		
	}
	
	
	
}
