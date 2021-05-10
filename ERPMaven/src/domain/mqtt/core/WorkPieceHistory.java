package domain.mqtt.core;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class WorkPieceHistory {

	private List<WorkPieceHistoryEntry> entrys = new ArrayList<>();

	public List<WorkPieceHistoryEntry> getEntrys() {
		return entrys;
	}

	public void setEntrys(List<WorkPieceHistoryEntry> entrys) {
		this.entrys = entrys;
	}

	public void addHistoryEntryOfAWP(ActiveWorkPiece awp, String ts) {

		changeUnassignedEntrysOfAWP(awp);

		if (awp.getWorkpiece() == null) {
			WorkPiece wp = new WorkPiece(awp.getPostingdirection(), "", "");
			awp.setWorkpiece(wp);
		}

		int listplace = -1;
		// prüfen, ob Teil schon eine History hat
		for (int i = 0; i < entrys.size(); i++) {
			if (awp.getWorkpiece().getId().equals(entrys.get(i).getWpID())) {
				listplace = i;
				break;
			}
		}

		if (listplace == -1) {
			WorkPieceHistoryEntry wphe = new WorkPieceHistoryEntry(awp.getWorkpiece().getId(), awp.getLocation(),
					awp.getStockplace(), ts, awp.getState());
			entrys.add(wphe);
			System.out.println("Eintrag WPH gemacht " + ts);
		} else {
			if (!entrys.get(listplace).getTimestamps().get(entrys.get(listplace).getTimestamps().size() - 1).equals(ts))
			// auf doppelte Einträge prüfen, MQTT manchmal doppelt
			{
				{
					entrys.get(listplace).addEntry(awp.getLocation(), awp.getStockplace(), ts, awp.getState());
					System.out.println("Eintrag WPH gemacht " + ts);
				}
			}
		}
	}

	public void addHistoryEntry(String id, String location, String stockplace, String ts, String state) {
		int listplace = -1;
		for (int i = 0; i < entrys.size(); i++) {
			if (id.equals(entrys.get(i).getWpID())) {
				listplace = i;
				break;
			}
		}
		if (listplace == -1) {
			WorkPieceHistoryEntry wphe = new WorkPieceHistoryEntry(id, location, stockplace, ts, state);
			entrys.add(wphe);
		} else {
			entrys.get(listplace).addEntry(location, stockplace, ts, state);
		}
	}

	public void changeUnassignedEntrysOfAWP(ActiveWorkPiece awp) {
		if ((awp.getWorkpiece() != null) && (!awp.getWorkpiece().getId().equals(awp.getPostingdirection()))) {

			WorkPieceHistoryEntry temp = new WorkPieceHistoryEntry();
			for (int i = 0; i < entrys.size(); i++) {
				if (awp.getPostingdirection().equals(entrys.get(i).getWpID())) {

					temp.setLocations(entrys.get(i).getLocations());
					temp.setTimestamps(entrys.get(i).getTimestamps());
					temp.setStockplaces(entrys.get(i).getStockplaces());
					temp.setStates(entrys.get(i).getStates());
					temp.setWpID(awp.getWorkpiece().getId());

					entrys.remove(i);

					for (int j = 0; j < temp.getTimestamps().size(); j++) {
						addHistoryEntry(temp.getWpID(), temp.getLocations().get(j), temp.getStockplaces().get(j),
								temp.getTimestamps().get(j), temp.getStates().get(j));
					}
					break;
				}
			}
		}
	}

	public List<WorkPieceHistoryEntry> getEntrysOf(String id) {

		List<WorkPieceHistoryEntry> res = new ArrayList<>();

		for (int i = 0; i < entrys.size(); i++) {
			if (id.equals(entrys.get(i).getWpID())) {
				res.add(entrys.get(i));
			}
		}
		return res;
	}

	public WorkPieceHistoryEntry getLastEntryOf(String id) {

		List<WorkPieceHistoryEntry> all = getEntrysOf(id);
		WorkPieceHistoryEntry res = new WorkPieceHistoryEntry();
		res.addEntry(all.get(0).getLocations().get(all.get(0).getLocations().size() - 1),
				all.get(0).getStockplaces().get(all.get(0).getStockplaces().size() - 1),
				all.get(0).getTimestamps().get(all.get(0).getTimestamps().size() - 1),
				all.get(0).getStates().get(all.get(0).getStates().size() - 1));
		return res;
	}

	public void deleteEntrysOf(String id) {

		for (int i = 0; i < entrys.size(); i++) {
			if (id.equals(entrys.get(i).getWpID())) {
				entrys.remove(i);
				break;
			}
		}
	}

}
