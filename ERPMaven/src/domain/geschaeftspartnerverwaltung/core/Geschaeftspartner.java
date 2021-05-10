package domain.geschaeftspartnerverwaltung.core;

import domain.betriebsmittelverwaltung.core.InvalidBetriebsmittelException;

public class Geschaeftspartner {

	private int partnernummer;
	private Adresse adresse;

	public int getPartnernummer() {
		return partnernummer;
	}

	public void setPartnernummer(int partnernummer) {
		this.partnernummer = partnernummer;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String toString() {
		return "" + partnernummer + adresse.toString();
	}

	void validateGeschaeftspartner() throws InvalidGeschaeftspartnerException {

		if (this.getAdresse() == null) {
			throw new InvalidGeschaeftspartnerException("Adresse fehlt!");
		}
		this.getAdresse().validateAdresse();
	}

}
