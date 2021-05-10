package domain.bedarfsplanung.core;

import java.util.LinkedList;
import java.util.List;

public class Nettobedarf {
	
	private int menge;
	private List<Auftrag> auftraege;
	
	public Nettobedarf() {
		auftraege = new LinkedList<>();
	}

	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	public List<Auftrag> getAuftraege() {
		return auftraege;
	}
	public void setAuftraege(List<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}
	public void addAuftrag(Auftrag a) {
		auftraege.add(a);
	}
	
}
