package domain.geschaeftspartnerverwaltung.core;

import domain.betriebsmittelverwaltung.core.InvalidBetriebsmittelException;

public class Kunde extends Geschaeftspartner {

	private String name;
	private String vorname;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	@Override
	public String toString() {
		return "" + name + ", " + vorname + super.toString();
	}
	void validateGeschaeftspartner() throws InvalidGeschaeftspartnerException{
		
		super.validateGeschaeftspartner();
		
		if (this.getName() == null) {
			throw new InvalidGeschaeftspartnerException("Nachname fehlt!");
		}
		if (this.getVorname() == null) {
			throw new InvalidGeschaeftspartnerException("Vorname fehlt!");
		}
		
	}
	
	
}
