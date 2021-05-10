package domain.mqtt.core;

public class VGRstate {

	private Integer active;
	private Integer code;
	private String description;
	private String station;
	private String target;
	private String ts;

	public VGRstate() {
	}

	public VGRstate(Integer active, Integer code, String description, String station, String target, String ts) {
		super();
		this.active = active;
		this.code = code;
		this.description = description;
		this.station = station;
		this.target = target;
		this.ts = ts;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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
		sb.append(VGRstate.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("active");
		sb.append('=');
		sb.append(((this.active == null) ? "<null>" : this.active));
		sb.append(',');
		sb.append("code");
		sb.append('=');
		sb.append(((this.code == null) ? "<null>" : this.code));
		sb.append(',');
		sb.append("description");
		sb.append('=');
		sb.append(((this.description == null) ? "<null>" : this.description));
		sb.append(',');
		sb.append("station");
		sb.append('=');
		sb.append(((this.station == null) ? "<null>" : this.station));
		sb.append(',');
		sb.append("target");
		sb.append('=');
		sb.append(((this.target == null) ? "<null>" : this.target));
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