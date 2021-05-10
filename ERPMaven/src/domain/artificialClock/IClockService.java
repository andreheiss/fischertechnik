package domain.artificialClock;

public interface IClockService {

	public void stop();
	public void run();
	public String getTime();
	public void anmelden(IObserver o);
	public void abmelden(IObserver o);
	
}
