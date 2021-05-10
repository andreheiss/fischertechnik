package domain.bedarfsplanung.core;


import java.time.LocalDateTime;

public class Beschaffungsauftrag extends Auftrag{

	private Fremdbezugsteil teil;
	private LocalDateTime lieferzeitpunkt;
	
	
	public Fremdbezugsteil getTeil() {
		return teil;
	}
	public void setTeil(Fremdbezugsteil teil) {
		this.teil = teil;
	}
	public LocalDateTime getLieferzeitpunkt() {
		return lieferzeitpunkt;
	}
	public void setLieferzeitpunkt(LocalDateTime lieferzeitpunkt) {
		this.lieferzeitpunkt = lieferzeitpunkt;
	}
	
	
}
