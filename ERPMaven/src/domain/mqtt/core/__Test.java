package domain.mqtt.core;

import java.util.ArrayList;
import java.util.List;

public class __Test {

	
	
	public static void main(String[] args) {
		WorkPieceHistory wph = new WorkPieceHistory();
		
		ActiveWorkPiece awp = new ActiveWorkPiece();
		awp.setStockplace("Z9");
		awp.setPostingdirection("hoch");
		awp.setLocation("Location");
		
		wph.addHistoryEntryOfAWP(awp, "leer");
		WorkPiece wp = new WorkPiece("", "RAW", "RED");
		WorkPiece wp1 = new WorkPiece("456", "RAW", "RED");
		awp.setWorkpiece(wp);
		awp.setLocation("SHIPPED");
		awp.setPostingdirection("aus");
		
		wph.addHistoryEntryOfAWP(awp, "1. Mal");
		wph.addHistoryEntryOfAWP(awp, "2. Mal");
		awp.setLocation("Location");
		wph.addHistoryEntryOfAWP(awp, "Unassigned to Assigned");
		
	
		
		

		
		
	}
}
