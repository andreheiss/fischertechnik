package domain.mqtt.core;

import java.util.List;

public class NFC {

	private List<History> history = null;
	private String ts;
	private WorkPiece workpiece;

	public NFC() {
	}

	public NFC(List<History> history, String ts, WorkPiece workpiece) {
		this.history = history;
		this.ts = ts;
		this.workpiece = workpiece;
	}

	public List<History> getHistory() {
		return history;
	}

	public void setHistory(List<History> history) {
		this.history = history;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public WorkPiece getWorkpiece() {
		return workpiece;
	}

	public void setWorkPiece(WorkPiece workpiece) {
		this.workpiece = workpiece;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(NFC.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("history");
		sb.append('=');
		sb.append(((this.history == null) ? "<null>" : this.history));
		sb.append(',');
		sb.append("ts");
		sb.append('=');
		sb.append(((this.ts == null) ? "<null>" : this.ts));
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