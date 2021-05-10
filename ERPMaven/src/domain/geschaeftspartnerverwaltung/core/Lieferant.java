package domain.geschaeftspartnerverwaltung.core;

public class Lieferant extends Geschaeftspartner{

	private String firma;

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	public String toString() {
		return "" + firma + ", " + super.toString();
	}
	void validateGeschaeftspartner() throws InvalidGeschaeftspartnerException{
		
		super.validateGeschaeftspartner();
		
		if (this.getFirma() == null) {
			throw new InvalidGeschaeftspartnerException("Firma fehlt!");
		}
	}
}
