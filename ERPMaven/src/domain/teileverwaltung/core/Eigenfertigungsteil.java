package domain.teileverwaltung.core;

import java.util.LinkedList;
import java.util.List;

public class Eigenfertigungsteil extends Teil{

	private List<Stuecklistenelement> baukastenstueckliste;
	
	public Eigenfertigungsteil() {
		baukastenstueckliste = new LinkedList<>();
	}
	//zum anzeigen der stueckliste
	public List<Stuecklistenelement> getBaukastenstueckliste(){
		return baukastenstueckliste;
	}

	public void setBaukastenstueckliste(List<Stuecklistenelement> baukastenstueckliste) {
		this.baukastenstueckliste = baukastenstueckliste;
	}
	//fuer aenderungen -> ganze Liste ersetzen
	public void replaceBaukastenstueckliste(List<Stuecklistenelement> l) {
		baukastenstueckliste = l;
	}
	public String toString() {
		return super.getTeilenummer() +", Eigenfertigungsteil ," +super.toString();
	}
}