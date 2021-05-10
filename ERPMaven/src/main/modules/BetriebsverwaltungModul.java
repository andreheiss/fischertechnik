package main.modules;

import com.google.inject.AbstractModule;

import datenschicht.betriebsverwaltung.WerkHibernate;
import domain.betriebsverwaltung.core.WerkServiceImpl;
import domain.betriebsverwaltung.provided.IWerkService;
import domain.betriebsverwaltung.required.IWerkHibernate;

public class BetriebsverwaltungModul extends AbstractModule{

	protected void configure() {
		
		bind(IWerkService.class).to(WerkServiceImpl.class);
		bind(IWerkHibernate.class).to(WerkHibernate.class);
	}
	
}
