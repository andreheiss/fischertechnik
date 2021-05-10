package domain.mqtt.required;

import domain.mqtt.core.WorkPiece;

public interface ILagerNachricht {
// OUT

	public void einlagerInfo(WorkPiece workpiece, String lagerplatz);
// 	gibt zurück, welches und wo ein Teil eingelagert wurde
	
	public void auslagerInfo(String lagerplatz);
// 	gibt zurück, wo ein Teil ausgelagert wurde

}
