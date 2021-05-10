package domain.geschaeftspartnerverwaltung.core;

import java.time.LocalDate;

public class Adresse {

	private int adressnummer;
	private LocalDate gueltigVon;
	private LocalDate gueltigBis;
	private String strasse;
	private String hausnummer;
	private String plz;
	private String ort;
	
	
	public int getAdressnummer() {
		return adressnummer;
	}
	public void setAdressnummer(int adressnummer) {
		this.adressnummer = adressnummer;
	}
	public LocalDate getGueltigBis() {
		return gueltigBis;
	}
	public void setGueltigBis(LocalDate gueltigBis) {
		this.gueltigBis = gueltigBis;
	}
	public LocalDate getGueltigVon() {
		return gueltigVon;
	}
	public void setGueltigVon(LocalDate gueltigVon) {
		this.gueltigVon = gueltigVon;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public String getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	


	@Override
	public String toString() {
		return "" + adressnummer + ", =" + gueltigBis + ", " + gueltigVon
				+ ", " + strasse + ", " + hausnummer + ", " + plz + ", " + ort;
	}
	void validateAdresse() throws InvalidGeschaeftspartnerException{ 

		if (this.getGueltigVon() == null) {

			throw new InvalidGeschaeftspartnerException("GueltigVon fehlt!");
		}

		if (this.getGueltigBis() == null) {
			throw new InvalidGeschaeftspartnerException("GueltigBis fehlt!");
		}
		if (getGueltigBis().compareTo(getGueltigVon()) < 0) {
			throw new InvalidGeschaeftspartnerException("ungueltiger Zeitraum!");
		}
		if (this.getStrasse() == null) {
			throw new InvalidGeschaeftspartnerException("Adresse fehlt!");
		}
		if (this.getHausnummer() == null) {
			throw new InvalidGeschaeftspartnerException("Adresse fehlt!");
		}
		if (this.getPlz() == null) {
			throw new InvalidGeschaeftspartnerException("Adresse fehlt!");
		}
		if (this.getOrt() == null) {
			throw new InvalidGeschaeftspartnerException("Adresse fehlt!");
		}
	}
	
}
