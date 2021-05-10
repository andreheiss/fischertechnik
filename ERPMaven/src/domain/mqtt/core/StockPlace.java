package domain.mqtt.core;

public class StockPlace {
	
	String location;
	WorkPiece workpiece;

	public StockPlace (String location, WorkPiece workpiece) {
		this.location = location;
		this.workpiece = workpiece;
	}
	
	public String toString() {
		if (workpiece == null) {
			return "{ location: " + location + " workpiece:  null } ";
		}
		return "{ location: " + location + ", workpiece: " + workpiece.toString() + " } ";
	}
	
}