package domain.bedarfsplanung.core;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Kundenauftrag {

	private int kundenauftragsnummer;
	private List<Kundenauftragsposition> kundenauftragspositionen;
	private LocalDateTime lieferzeitpunkt;
	
	public Kundenauftrag() {
		kundenauftragspositionen = new LinkedList<>();
	}
	
	public LocalDateTime getLieferzeitpunkt() {
		return lieferzeitpunkt;
	}
	public void setLieferdatum(LocalDateTime lieferzeitpunkt) {
		this.lieferzeitpunkt = lieferzeitpunkt;
	}
	public int getKundenauftragsnummer() {
		return kundenauftragsnummer;
	}
	public void setKundenauftragsnummer(int kundenauftragsnummer) {
		this.kundenauftragsnummer = kundenauftragsnummer;
	}
	public List<Kundenauftragsposition> getKundenauftragspositionen() {
		return kundenauftragspositionen;
	}
	public void setKundenauftragspositionen(List<Kundenauftragsposition> kundenauftragspositionen) {
		this.kundenauftragspositionen = kundenauftragspositionen;
	}
	
	
}
