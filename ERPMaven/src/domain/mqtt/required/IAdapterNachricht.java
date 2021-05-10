package domain.mqtt.required;

import domain.mqtt.core.WorkPiece;
import domain.mqtt.core.WorkPieceHistoryEntry;

public interface IAdapterNachricht {
// OUT

	public void einlagerInfo(WorkPiece workpiece, String lagerplatz);
// 	gibt zur�ck, welches und wo ein Teil eingelagert wurde
	
	public void auslagerInfo(String lagerplatz);
// 	gibt zur�ck, wo ein Teil ausgelagert wurde
	
	public void historieEintragInfo(WorkPieceHistoryEntry entry);

}
