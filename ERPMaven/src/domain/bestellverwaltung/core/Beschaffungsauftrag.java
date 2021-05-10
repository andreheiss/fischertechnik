package domain.bestellverwaltung.core;

import java.time.LocalDateTime;

import domain.shared.Auftragstatus;

public class Beschaffungsauftrag{

	private int auftragsnummer;
	private Auftragstatus status;
	private int teilenummer;  //not null
	private int menge;
	private LocalDateTime lieferzeitpunkt;
	private LocalDateTime bestellzeitpunkt;
	
	public int getAuftragsnummer() {
		return auftragsnummer;
	}
	public void setAuftragsnummer(int auftragsnummer) {
		this.auftragsnummer = auftragsnummer;
	}
	public Auftragstatus getStatus() {
		return status;
	}
	public void setStatus(Auftragstatus status) {
		this.status = status;
	}
	public int getTeilenummer() {
		return teilenummer;
	}
	public void setTeilenummer(int teilenummer) {
		this.teilenummer = teilenummer;
	}
	public LocalDateTime getLieferzeitpunkt() {
		return lieferzeitpunkt;
	}
	public void setLieferzeitpunkt(LocalDateTime lieferzeitpunkt) {
		this.lieferzeitpunkt = lieferzeitpunkt;
	}
	public LocalDateTime getBestellzeitpunkt() {
		return bestellzeitpunkt;
	}
	public void setBestellzeitpunkt(LocalDateTime bestellzeitpunkt) {
		this.bestellzeitpunkt = bestellzeitpunkt;
	}
	
	
	
}
