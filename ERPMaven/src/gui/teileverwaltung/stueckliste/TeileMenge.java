package gui.teileverwaltung.stueckliste;

import domain.teileverwaltung.core.Teil;

public class TeileMenge {
	
	private Teil teil;
	private int menge;
	private String bezeichnung;
	private int teilenummer;
	
	public TeileMenge(Teil t) {
		teil = t;
		menge = 1;
		bezeichnung = t.getBezeichnung();
		teilenummer = t.getTeilenummer();
	}

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

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getTeilenummer() {
		return teilenummer;
	}

	public void setTeilenummer(int teilenummer) {
		this.teilenummer = teilenummer;
	}
	

}
