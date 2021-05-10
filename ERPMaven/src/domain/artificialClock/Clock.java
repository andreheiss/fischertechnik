package domain.artificialClock;

import java.util.LinkedList;

public class Clock extends Thread implements IObservable {

	private LinkedList<IObserver> beobachter;
	private final int SLEEPING_TIME = 1000;
	private int tag; // startdatum
	private int std; // startuhrzeit
	private int min; // startuhrzeit
	private int sek; // startuhrzeit
	private int dayduration; // Dauer eines Tages in Stunden
	private int hourduration; // Dauer einer Stunde in Minuten
	private boolean stop;

	public Clock(int t, int h, int m, int s, int hdur, int mdur) {
		stop = false;
		tag = t;
		std = h;
		min = m;
		sek = s;
		dayduration = hdur;
		hourduration = mdur;
		beobachter = new LinkedList<>();
	}

	public void run() {
		stop = false;

		while (!stop) {
			try {
				// System.out.println("Tag: " + tag + ", Zeit: "+std+":"+min+":"+sek);
				benachrichtigen();
				super.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			if (sek < 59) {
				sek++;
			} else {
				sek = 0;
				if (min < hourduration - 1) {
					min++;
				} else {
					min = 0;
					if (std < dayduration - 1) {
						std++;
					} else {
						std = 0;
						tag++;
					}
				}
			}

		}

	}

	public void stopClock() {
		stop = true;

	}

	public Clock continueClock() {
		Clock c = new Clock(tag, std, min, sek, dayduration, hourduration);
		c.setObserverList(beobachter);
		return c;
	}

	public String getTime() {
		return "Tag\n" + tag + "\n" + "Uhrzeit\n" + std + ":" + min + ":" + sek;
	}

	@Override
	public void anmelden(IObserver o) {
		beobachter.add(o);

	}

	@Override
	public void abmelden(IObserver o) {
		beobachter.remove(o);

	}

	public void benachrichtigen() {
		for (IObserver o : beobachter) {
			o.aktualisieren();
		}
	}

	public void setObserverList(LinkedList<IObserver> o) {
		beobachter = o;
	}
	public boolean isStopped() {
		return stop;
	}

}