package domain.monitoring.core;

import java.time.LocalDateTime;

import domain.shared.*;

public class Monitor {
	
	private MonitoringZustand zustand;
	private MonitoringOrt ort;
	private LocalDateTime zeitpunkt;
	
	public MonitoringZustand getZustand() {
		return zustand;
	}
	public void setZustand(MonitoringZustand zustand) {
		this.zustand = zustand;
	}
	public MonitoringOrt getOrt() {
		return ort;
	}
	public void setOrt(MonitoringOrt ort) {
		this.ort = ort;
	}
	public LocalDateTime getZeitpunkt() {
		return zeitpunkt;
	}
	public void setZeitpunkt(LocalDateTime zeitpunkt) {
		this.zeitpunkt = zeitpunkt;
	}

}
