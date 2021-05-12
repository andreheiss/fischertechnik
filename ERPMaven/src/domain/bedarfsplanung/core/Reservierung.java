package domain.bedarfsplanung.core;

public class Reservierung {
	
	private int reservierungsummer;
	private Bruttobedarf bruttobedarf;
	private Teil teil;
	private int menge;
	
	
	public int getReservierungsummer() {
		return reservierungsummer;
	}
	public void setReservierungsummer(int reservierungsummer) {
		this.reservierungsummer = reservierungsummer;
	}
	public Teil getTeil() {
		return teil;
	}
	public void setTeil(Teil teil) {
		this.teil = teil;
	}
	public Bruttobedarf getBruttobedarf() {
		return bruttobedarf;
	}
	public void setBruttobedarf(Bruttobedarf bruttobedarf) {
		this.bruttobedarf = bruttobedarf;
	}
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	
	
	
	
}
