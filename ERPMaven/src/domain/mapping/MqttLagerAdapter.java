package domain.mapping;

import com.google.inject.Inject;

import domain.lagerverwaltung.core.Lagerplatz;
import domain.lagerverwaltung.core.Lagerteil;
import domain.lagerverwaltung.provided.ILagerService;
import domain.mqtt.core.WorkPiece;
import domain.mqtt.required.ILagerNachricht;
import domain.shared.Farbe;
import domain.teileverwaltung.core.Fremdbezugsteil;
import domain.teileverwaltung.core.Teil;
import domain.teileverwaltung.providedservices.ITeilService;

import java.util.List;

public class MqttLagerAdapter implements ILagerNachricht {

	private ILagerService ils;
	private ITeilService its;
	
	@Inject
	public MqttLagerAdapter (ILagerService ils, ITeilService its) {
		this.ils = ils;		
		this.its = its;		
	}
	
	@Override
	public void einlagerInfo(WorkPiece wp, String lp) {
		
		Teil t = teilUmwandeln(wp);
		Lagerplatz l = lagerplatzUmwandeln(lp);
	//	Lagerteil lt = new Lagerteil();
	//	lt.setTeilenummer(t.getTeilenummer());
	//	l.setTeil(lt);
		l.setTeilenummer(t.getTeilenummer());
		ils.einlagern(l);
		
	}

	@Override
	public void auslagerInfo(String lp) {
		
		Lagerplatz l = lagerplatzUmwandeln(lp);
		// 	l.auslagern();		
		ils.auslagern(l);
	}
	
	public Teil teilUmwandeln(WorkPiece wp) {
		Fremdbezugsteil t = new Fremdbezugsteil();
		switch (wp.getType()) {
		case "RED":
			t.setFarbe(Farbe.Rot);
			break;
		case "WHITE":
			t.setFarbe(Farbe.Weiss);
			break;
		case "BLUE":
			t.setFarbe(Farbe.Blau);
			break;
		}
		List<Teil> teil = its.getTeil(t);
		
		return teil.get(0);
	}
	
	public Lagerplatz lagerplatzUmwandeln(String lp) {
		Lagerplatz l = new Lagerplatz();
		switch (lp.charAt(0)) {
		case 'A':
			l.setPosX(1);
			break;
		case 'B':
			l.setPosX(2);
			break;
		case 'C':
			l.setPosX(3);
			break;
		}
		switch (lp.charAt(1)) {
		case 'A':
			l.setPosY(1);
			break;
		case 'B':
			l.setPosY(2);
			break;
		case 'C':
			l.setPosY(3);
			break;
		}
		List<Lagerplatz> lagerplatz = ils.getLagerplatz(l);
		return lagerplatz.get(0);
		
	}
	
}
