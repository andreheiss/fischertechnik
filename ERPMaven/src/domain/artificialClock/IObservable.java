package domain.artificialClock;

public interface IObservable{

	public void anmelden(IObserver o);
	public void abmelden(IObserver o);
}
