package domain.mqtt.core;

import java.util.ArrayList;
import java.util.List;

public class WorkPieceHistoryEntry {
	
	String wpID;
	
	private List<String> locations = new ArrayList<>();
	private List<String> stockplaces = new ArrayList<>();
	private List<String> timestamps = new ArrayList<>();
	private List<String> states = new ArrayList<>();
	
	public WorkPieceHistoryEntry(String wpID, String location, String stockplace, String ts, String state) {
		this.wpID = wpID;
		locations.add(location);
		stockplaces.add(stockplace);
		timestamps.add(ts);
		states.add(state);
	}
	public WorkPieceHistoryEntry(String wpID, String location, String ts, String state) {
		this.wpID = wpID;
		locations.add(location);
		timestamps.add(ts);
		states.add(state);
	}
	
	public WorkPieceHistoryEntry() {		
	}
	
	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public List<String> getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(List<String> timestamps) {
		this.timestamps = timestamps;
	}

	
	public void addEntry(String location, String stockplace, String ts, String state) {
		locations.add(location);
		stockplaces.add(stockplace);
		timestamps.add(ts);
		states.add(state);
	}

	public String getWpID() {
		return wpID;
	}

	public void setWpID(String wpID) {
		this.wpID = wpID;
	}
	public List<String> getStockplaces() {
		return stockplaces;
	}
	public void setStockplaces(List<String> stockplaces) {
		this.stockplaces = stockplaces;
	}
	public List<String> getStates() {
		return states;
	}
	public void setStates(List<String> states) {
		this.states = states;
	}

}
