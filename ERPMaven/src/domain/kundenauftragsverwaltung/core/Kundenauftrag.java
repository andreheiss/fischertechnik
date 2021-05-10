package domain.kundenauftragsverwaltung.core;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import domain.shared.Auftragstatus;

public class Kundenauftrag {

	private int kundenauftragsnummer;
	private LocalDateTime lieferzeitpunkt;
	private int kundennummer;
	private List<Kundenauftragsposition> positionen;
	private Auftragstatus status;
	
	public Kundenauftrag() {
		positionen = new LinkedList<>();
	}
	
	public int getKundenauftragsnummer() {
		return kundenauftragsnummer;
	}
	public void setKundenauftragsnummer(int kundenauftragsnummer) {
		this.kundenauftragsnummer = kundenauftragsnummer;
	}
	public LocalDateTime getLieferzeitpunkt() {
		return lieferzeitpunkt;
	}
	public void setLieferzeitpunkt(LocalDateTime lieferzeitpunkt) {
		this.lieferzeitpunkt = lieferzeitpunkt;
	}
	public int getKundennummer() {
		return kundennummer;
	}
	public void setKundennummer(int kundennummer) {
		this.kundennummer = kundennummer;
	}
	public List<Kundenauftragsposition> getPositionen() {
		return positionen;
	}
	public void setPositionen(List<Kundenauftragsposition> positionen) {
		this.positionen = positionen;
	}
	public Auftragstatus getStatus() {
		return status;
	}
	void setStatus(Auftragstatus status) {
		this.status = status;
	}
	
	
}
