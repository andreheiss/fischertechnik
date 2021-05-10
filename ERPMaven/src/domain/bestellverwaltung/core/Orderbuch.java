package domain.bestellverwaltung.core;

import java.time.LocalDate;

public class Orderbuch {

	private int orderbuchnummer;
	private int lieferantennummer;
	private int fremdbezugsteilnummer;
	private LocalDate gueltigVon;
	private LocalDate gueltigBis;
	private int lieferdauer;  //in Sekunden?
	
	public int getOrderbuchnummer() {
		return orderbuchnummer;
	}
	public void setOrderbuchnummer(int orderbuchnummer) {
		this.orderbuchnummer = orderbuchnummer;
	}
	public int getLieferantennummer() {
		return lieferantennummer;
	}
	public void setLieferantennummer(int lieferantennummer) {
		this.lieferantennummer = lieferantennummer;
	}
	public int getFremdbezugsteilnummer() {
		return fremdbezugsteilnummer;
	}
	public void setFremdbezugsteilnummer(int fremdbezugsteilnummer) {
		this.fremdbezugsteilnummer = fremdbezugsteilnummer;
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
	public int getLieferdauer() {
		return lieferdauer;
	}
	public void setLieferdauer(int lieferdauer) {
		this.lieferdauer = lieferdauer;
	}
	
	
}
