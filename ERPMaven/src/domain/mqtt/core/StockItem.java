package domain.mqtt.core;

public class StockItem {

	private String location;
	private WorkPiece workpiece;

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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(StockItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("location");
		sb.append('=');
		sb.append(((this.location == null) ? "<null>" : this.location));
		sb.append(',');
		sb.append("workpiece");
		sb.append('=');
		sb.append(((this.workpiece == null) ? "<null>" : this.workpiece));
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

}
