package domain.mqtt.core;

public class ActiveWorkPiece {
	
	WorkPiece workpiece;
	
	String location;
	// wo sich das Teil gerade befindet
	
	String postingdirection;
	// ein oder aus (Buchungsrichtung)
	
	String stockplace;
	
	String state;
	
	public ActiveWorkPiece() {
		
	}
	
	public ActiveWorkPiece(String postingdirection) {
		this.postingdirection = postingdirection;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public WorkPiece getWorkpiece() {
		return workpiece;
	}

	public void setWorkpiece(WorkPiece workpiece) {
		this.workpiece = workpiece;
	}

	public String getPostingdirection() {
		return postingdirection;
	}

	public void setPostingdirection(String postingdirection) {
		this.postingdirection = postingdirection;
	}

	public String getStockplace() {
		return stockplace;
	}

	public void setStockplace(String stockplace) {
		this.stockplace = stockplace;
	}

	public void clearStockplace() {
		this.stockplace = null;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public void clearAWP() {
		this.workpiece = null;
		this.location = null;
		this.stockplace = null;
		this.state = null;
		
	}
}
