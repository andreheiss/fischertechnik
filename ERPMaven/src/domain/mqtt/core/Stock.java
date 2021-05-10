package domain.mqtt.core;

import java.util.ArrayList;
import java.util.List;

public class Stock {

	private List<StockItem> stockItems = null;
	private String ts;

	public Stock() {
	}

	public Stock(List<StockItem> stockItems, String ts) {

		this.stockItems = stockItems;
		this.ts = ts;
	}

	public List<StockItem> getStockItems() {
		return stockItems;
	}

	public void setStockItems(List<StockItem> stockItems) {
		this.stockItems = stockItems;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Stock.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("stockItems");
		sb.append('=');
		sb.append(((this.stockItems == null) ? "<null>" : this.stockItems));
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

	public List<WorkPiece> getWorkpieces() {
		List<WorkPiece> wpList = new ArrayList<>();
		for (int i = 0; i < stockItems.size(); i++) {
			if (stockItems.get(i).getWorkpiece() != null) {
				wpList.add(stockItems.get(i).getWorkpiece());
			}
		}
		return wpList;
		
		
	}
	public String getStockplaceOf (WorkPiece workpiece) {
		String res = "";
		for (int i = 0; i < stockItems.size(); i++) {
			if ((stockItems.get(i).getWorkpiece() != null) && (stockItems.get(i).getWorkpiece().getId() == (workpiece.getId()))) {
				res = stockItems.get(i).getLocation();
				break;
			}
		}
		return res;
		
	}
}
