package domain.lagerverwaltung.core;

public class Lagerplatz {

	private int lagerplatznummer;
	private int posX; //start with 1
	private int posY; //start with 1
	private Lagerteil teil;		
	
	
	public int getLagerplatznummer() {
		return lagerplatznummer;
	}
	public void setLagerplatznummer(int lagerplatznummer) {
		this.lagerplatznummer = lagerplatznummer;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public Lagerteil getTeil() {
		return teil;
	}
	public void setTeil(Lagerteil teil) {
		this.teil = teil;
	}
	
	
	
}
