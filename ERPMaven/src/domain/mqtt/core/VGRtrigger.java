package domain.mqtt.core;

public class VGRtrigger {

	private Integer code;
	private String ts;
	private WorkPiece workpiece;

	public VGRtrigger() {
	}

	public VGRtrigger(Integer code, String ts, WorkPiece workpiece) {

		this.code = code;
		this.ts = ts;
		this.workpiece = workpiece;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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

	public void setWorkpiece(WorkPiece workpiece) {
		this.workpiece = workpiece;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(VGRtrigger.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("code");
		sb.append('=');
		sb.append(((this.code == null) ? "<null>" : this.code));
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