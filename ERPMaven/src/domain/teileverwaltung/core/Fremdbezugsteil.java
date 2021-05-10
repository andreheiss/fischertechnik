package domain.teileverwaltung.core;



public class Fremdbezugsteil extends Teil{
	//Lieferant entfernt, fuer Teileverwaltung irrelevantes Attribut
		
	public String toString() {
		return super.getTeilenummer() +", Fremdbezugsteil ," +super.toString();
	}
	
	
}
