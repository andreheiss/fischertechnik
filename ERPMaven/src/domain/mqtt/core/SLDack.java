package domain.mqtt.core;

public class SLDack {

	private Integer code;
	private Integer colorValue;
	private String ts;
	private String type;

	public SLDack() {
	}

	public SLDack(Integer code, Integer colorValue, String ts, String type) {
		super();
		this.code = code;
		this.colorValue = colorValue;
		this.ts = ts;
		this.type = type;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getColorValue() {
		return colorValue;
	}

	public void setColorValue(Integer colorValue) {
		this.colorValue = colorValue;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(SLDack.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("code");
		sb.append('=');
		sb.append(((this.code == null) ? "<null>" : this.code));
		sb.append(',');
		sb.append("colorValue");
		sb.append('=');
		sb.append(((this.colorValue == null) ? "<null>" : this.colorValue));
		sb.append(',');
		sb.append("ts");
		sb.append('=');
		sb.append(((this.ts == null) ? "<null>" : this.ts));
		sb.append(',');
		sb.append("type");
		sb.append('=');
		sb.append(((this.type == null) ? "<null>" : this.type));
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}
}