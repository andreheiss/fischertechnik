package domain.mqtt.core;

public class HBWack {
	private Integer code;
	private WorkPiece workpiece;
	private String ts;

	public HBWack() {
	}

	public HBWack(Integer code, WorkPiece workpiece, String ts) {
		this.code = code;
		this.workpiece = workpiece;
		this.ts = ts;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public WorkPiece getWorkpiece() {
		return workpiece;
	}

	public void setWorkpiece(WorkPiece workpiece) {
		this.workpiece = workpiece;
	}
	
	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Cam.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("code");
		sb.append('=');
		sb.append(((this.code == null) ? "<null>" : this.code));
		sb.append(',');
		sb.append("workpiece");
		sb.append('=');
		sb.append(((this.workpiece == null) ? "<null>" : this.workpiece));
		sb.append(',');
		sb.append("ts");
		sb.append('=');
		sb.append(((this.ts == null) ? "<null>" : this.ts));
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

}
