package domain.monitoring.provided;

import domain.monitoring.core.Historie;
import domain.monitoring.core.Monitor;

public interface IMonitoringService {
	
	public Monitor getAktuellerZustandVonTeil(int id);
	public Historie getHistorieVonTeil(int id);
	
}
