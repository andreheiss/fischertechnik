package domain.produktionsplanung.core;

import java.util.List;


public class Fertigungsarbeitsgang {

	private int arbeitsgangnummer;
	private int bearbeitungszeit;		//in Sekunden
	private int ruestzeit;				//in Sekunden
	private List<Integer> betriebsmittelnummern;
	
	public int getArbeitsgangnummer() {
		return arbeitsgangnummer;
	}
	public void setArbeitsgangnummer(int arbeitsgangnummer) {
		this.arbeitsgangnummer = arbeitsgangnummer;
	}
	public int getBearbeitungszeit() {
		return bearbeitungszeit;
	}
	public void setBearbeitungszeit(int bearbeitungszeit) {
		this.bearbeitungszeit = bearbeitungszeit;
	}
	public int getRuestzeit() {
		return ruestzeit;
	}
	public void setRuestzeit(int ruestzeit) {
		this.ruestzeit = ruestzeit;
	}
	public List<Integer> getBetriebsmittelnummern() {
		return betriebsmittelnummern;
	}
	public void setBetriebsmittelnummern(List<Integer> betriebsmittelnummern) {
		this.betriebsmittelnummern = betriebsmittelnummern;
	}
	
	
	
	
}
