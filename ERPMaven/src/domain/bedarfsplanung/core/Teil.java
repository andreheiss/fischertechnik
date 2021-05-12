package domain.bedarfsplanung.core;

import java.util.LinkedList;
import java.util.List;

import domain.shared.Planungsart;

public class Teil {

	private int teilenummer;
	private Planungsart planart;
	
	
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
	
	
}
