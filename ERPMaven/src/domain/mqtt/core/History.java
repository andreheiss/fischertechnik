package domain.mqtt.core;

public class History {

	private Integer code;
	private String ts;

	public History() {
	}

	public History(Integer code, String ts) {
		
		this.code = code;
		this.ts = ts;
	}

	public Integer getCode() {
		return code;
//		100: Anlieferung Rohware
//		200: Qualitätskontrolle
//		300: Einlagerung
//		
//		400: Auslagerung
//		500: Ofen
//		600: Polierstation
//
//		700: Sortierung
//		800: Versand Ware
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(History.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("code");
		sb.append('=');
		sb.append(((this.code == null) ? "<null>" : this.code));
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