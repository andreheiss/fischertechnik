package domain.kundenauftragsverwaltung.core;


public class Kundenauftragsposition {

	private int positionsnummer;
	private int teilenummer;
	private int menge;
	
	public int getPositionsnummer() {
		return positionsnummer;
	}
	public void setPositionsnummer(int positionsnummer) {
		this.positionsnummer = positionsnummer;
	}
	public int getTeilenummer() {
		return teilenummer;
	}
	public void setTeilenummer(int teilenummer) {
		this.teilenummer = teilenummer;
	}
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	
	
	
}
