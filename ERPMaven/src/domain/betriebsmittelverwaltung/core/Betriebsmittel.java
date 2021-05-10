package domain.betriebsmittelverwaltung.core;

import java.time.LocalDate;

import domain.betriebsverwaltung.core.Werk;
import domain.shared.Betriebsmittelart;
import domain.teileverwaltung.core.InvalidTeilException;

public class Betriebsmittel {

	private int betriebsmittelnummer;
	private String bezeichnung;
	private LocalDate gueltigVon;
	private LocalDate gueltigBis;
	private Betriebsmittelart art;
	private BMWerk werk;

	public int getBetriebsmittelnummer() {
		return betriebsmittelnummer;
	}

	public void setBetriebsmittelnummer(int betriebsmittelnummer) {
		this.betriebsmittelnummer = betriebsmittelnummer;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
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

	public Betriebsmittelart getArt() {
		return art;
	}

	public void setArt(Betriebsmittelart art) {
		this.art = art;
	}

	public BMWerk getWerk() {
		return werk;
	}

	public void setWerk(BMWerk werk) {
		this.werk = werk;
	}

	void validateBetriebsmittel() throws InvalidBetriebsmittelException {

		if (this.getBezeichnung() == null) {
			throw new InvalidBetriebsmittelException("Bezeichnung fehlt!");
		}
		if (this.getArt() == null) {
			throw new InvalidBetriebsmittelException("Betriebsmittelart fehlt!");
		}
		if (this.getWerk()  == null) {
			throw new InvalidBetriebsmittelException("Werk fehlt!");
		}

		if (this.getGueltigVon() == null) {

			throw new InvalidBetriebsmittelException("GueltigVon fehlt!");
		}

		if (this.getGueltigBis() == null) {
			throw new InvalidBetriebsmittelException("GueltigBis fehlt!");
		}
		if (getGueltigBis().compareTo(getGueltigVon()) < 0) {
			throw new InvalidBetriebsmittelException("ungueltiger Zeitraum!");
		}

	}

}
