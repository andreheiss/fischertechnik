package domain.produktionsplanung.core;

import java.util.List;

import domain.arbeitsplanverwaltung.core.Betriebsmittel;

public class Fertigungsarbeitsgang {

	private int arbeitsgangnummer;
	private int bearbeitungszeit;		//in Sekunden
	private int ruestzeit;				//in Sekunden
	private List<Betriebsmittel> betriebsmittel;
	
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
	public List<Betriebsmittel> getBetriebsmittel() {
		return betriebsmittel;
	}
	public void setBetriebsmittel(List<Betriebsmittel> betriebsmittel) {
		this.betriebsmittel = betriebsmittel;
	}
	
	
	
	
}
