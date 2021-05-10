package domain.monitoring.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.shared.*;

public class Eintrag {

	private int teilID;
	private List<MonitoringZustand> zustaende;
	private List<MonitoringOrt> orte;
	private List<Lagerplatz> lagerplaetze;
	private List<LocalDateTime> zeitpunkte;

	public Eintrag(int teilID, List<MonitoringZustand> zustaende, List<MonitoringOrt> orte,
			List<Lagerplatz> lagerplaetze, List<LocalDateTime> zeitpunkte) {
		this.teilID = teilID;
		this.zustaende = zustaende;
		this.orte = orte;
		this.lagerplaetze = lagerplaetze;
		this.zeitpunkte = zeitpunkte;
	}

	public Eintrag(int teilID, MonitoringZustand zustand, MonitoringOrt ort, Lagerplatz lagerplatz,
			LocalDateTime zeitpunkt) {
		zustaende = new ArrayList<>();
		orte = new ArrayList<>();
		lagerplaetze = new ArrayList<>();
		zeitpunkte = new ArrayList<>();

		this.teilID = teilID;
		zustaende.add(zustand);
		orte.add(ort);
		lagerplaetze.add(lagerplatz);
		zeitpunkte.add(zeitpunkt);
	}

	public Eintrag() {
		// TODO Auto-generated constructor stub
	}

	public void EintragZuTeilHinzufuegen(MonitoringZustand zustand, MonitoringOrt ort, Lagerplatz lagerplatz,
			LocalDateTime zeitpunkt) {

		zustaende.add(zustand);
		orte.add(ort);
		lagerplaetze.add(lagerplatz);
		zeitpunkte.add(zeitpunkt);

	}

	public int getTeilID() {
		return teilID;
	}

	public void setTeilID(int teilID) {
		this.teilID = teilID;
	}

	public List<MonitoringZustand> getZustaende() {
		return zustaende;
	}

	public void setZustaende(List<MonitoringZustand> zustaende) {
		this.zustaende = zustaende;
	}

	public List<MonitoringOrt> getOrte() {
		return orte;
	}

	public void setOrte(List<MonitoringOrt> orte) {
		this.orte = orte;
	}

	public List<Lagerplatz> getLagerplaetze() {
		return lagerplaetze;
	}

	public void setLagerplaetze(List<Lagerplatz> lagerplaetze) {
		this.lagerplaetze = lagerplaetze;
	}

	public List<LocalDateTime> getZeitpunkte() {
		return zeitpunkte;
	}

	public void setZeitpunkte(List<LocalDateTime> zeitpunkte) {
		this.zeitpunkte = zeitpunkte;
	}
}
