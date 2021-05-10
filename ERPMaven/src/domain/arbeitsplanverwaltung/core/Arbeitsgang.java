package domain.arbeitsplanverwaltung.core;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Arbeitsgang {

	private int arbeitsgangnummer;
	private String bezeichnung;
	private int bearbeitungszeit;		//in Sekunden
	private int ruestzeit;				//in Sekunden
	private LocalDate gueltigVon;
	private LocalDate gueltigBis;
	private List<Betriebsmittel> betriebsmittel;
	
	public Arbeitsgang() {
		betriebsmittel = new LinkedList<>();
	}

	public int getArbeitsgangnummer() {
		return arbeitsgangnummer;
	}

	public void setArbeitsgangnummer(int arbeitsgangnummer) {
		this.arbeitsgangnummer = arbeitsgangnummer;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
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

	public LocalDate getGueltigVon() {
		return gueltigVon;
	}

	public void setGueltigVon(LocalDate gueltigVon) {
		this.gueltigVon = gueltigVon;
	}

	public LocalDate getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(LocalDate gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

	public List<Betriebsmittel> getBetriebsmittel() {
		return betriebsmittel;
	}

	public void setBetriebsmittel(List<Betriebsmittel> betriebsmittel) {
		this.betriebsmittel = betriebsmittel;
	}
	
}
