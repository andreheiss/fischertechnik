package domain.bedarfsplanung.core;

public class Kundenauftragsposition {

	private int kundenauftragspositionsnummer;
	private Teil teil;
	private int menge;
	private Bruttobedarf primaerbedarf;
	
	
	public int getKundenauftragspositionsnummer() {
		return kundenauftragspositionsnummer;
	}
	public void setKundenauftragspositionsnummer(int kundenauftragspositionsnummer) {
		this.kundenauftragspositionsnummer = kundenauftragspositionsnummer;
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
	
	public Bruttobedarf getPrimaerbedarf() {
		return primaerbedarf;
	}
	public void setPrimaerbedarf(Bruttobedarf primaerbedarf) {
		this.primaerbedarf = primaerbedarf;
	}
	
	
}
