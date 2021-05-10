package domain.mqtt.provided;

import domain.mqtt.core.WorkPiece;

public interface IMQTTService {
// IN

	public String produzieren(String Farbe);
//	gibt den Lagerort, wo das Teil war zurück

	public String getLocation(WorkPiece workpiece);
//	gibt den aktuellen Ort des Teils zurück (Lagerplatz oder Ort in der Fabrik, falls in Produktion oder beim Einlagern)
}
