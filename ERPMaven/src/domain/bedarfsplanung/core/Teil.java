package domain.bedarfsplanung.core;

import java.util.LinkedList;
import java.util.List;

import domain.shared.Planungsart;

public class Teil {

	private int teilenummer;
	private Planungsart planart;
	private List<Lagerplatz> lagerplaetze;
	private List<Reservierung> reservierungen;
	
	public Teil() {
		lagerplaetze = new LinkedList<>();
		reservierungen = new LinkedList<>();
	}
	
	public int getTeilenummer() {
		return teilenummer;
	}
	public void setTeilenummer(int teilenummer) {
		this.teilenummer = teilenummer;
	}
	public Planungsart getPlanart() {
		return planart;
	}
	public void setPlanart(Planungsart planart) {
		this.planart = planart;
	}
	public List<Lagerplatz> getLagerplaetze() {
		return lagerplaetze;
	}
	public void setLagerplaetze(List<Lagerplatz> lagerplaetze) {
		this.lagerplaetze = lagerplaetze;
	}
	public List<Reservierung> getReservierungen() {
		return reservierungen;
	}
	public void setReservierungen(List<Reservierung> reservierungen) {
		this.reservierungen = reservierungen;
	}
	public int getLagerbestand() {
		return lagerplaetze.size();
	}
	public void reservieren(Reservierung r) {
		//checken ob Menge vorhanden
		reservierungen.add(r);
	}
	
}
