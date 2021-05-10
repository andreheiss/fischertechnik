package domain.artificialClock;

public class ClockService implements IClockService{

	private Clock clock;
	
	public ClockService() {
		
		clock = new Clock(0,0,0,0,10,2);
		clock.start();
	}
	
	public void stop() {
		clock.stopClock();
	}
	public void run() {
		if(clock.isStopped()) {
		clock = clock.continueClock();
		clock.start();
		}
	}
	public String getTime() {
		return clock.getTime();
	}

	@Override
	public void anmelden(IObserver o) {
	clock.anmelden(o);
		
	}

	@Override
	public void abmelden(IObserver o) {
		clock.abmelden(o);
	}
	
	
}
