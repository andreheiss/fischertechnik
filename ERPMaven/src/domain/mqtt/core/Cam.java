package domain.mqtt.core;

public class Cam {

	private String data;
	private String ts;

	public Cam() {
	}

	public Cam(String data, String ts) {
		this.data = data;
		this.ts = ts;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
		sb.append("data");
		sb.append('=');
		sb.append(((this.data == null) ? "<null>" : this.data));
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