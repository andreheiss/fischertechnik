package domain.arbeitsplanverwaltung.core;

import java.util.LinkedList;
import java.util.List;

public class Eigenfertigungsteil extends Teil{

	private List<Stuecklistenelement> baukastenstueckliste;
	
	public Eigenfertigungsteil() {
		baukastenstueckliste = new LinkedList<Stuecklistenelement>();
	}

	public List<Stuecklistenelement> getBaukastenstueckliste() {
		return baukastenstueckliste;
	}

	public void setBaukastenstueckliste(List<Stuecklistenelement> baukastenstueckliste) {
		this.baukastenstueckliste = baukastenstueckliste;
	}
	
	
	
}
