package domain.teileverwaltung.core;

public class Stuecklistenelement {

	private Teil teil;
	private int menge;
	
	public Teil getTeil() {
		return teil;
	}
	public void setTeil(Teil teil) {
		this.teil = teil;
	}
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	
	public String toString() {
		return teil.getTeilenummer() + teil.getBezeichnung() + menge;
	}
	
}
