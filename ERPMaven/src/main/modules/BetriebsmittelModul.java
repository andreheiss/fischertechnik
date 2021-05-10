package main.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import datenschicht.betriebsmittelverwaltung.BetriebsmittelHibernate;
import datenschicht.betriebsmittelverwaltung.BetriebsmittelRepository;
import domain.betriebsmittelverwaltung.core.BetriebsmittelServiceImpl;
import domain.betriebsmittelverwaltung.provided.IBetriebsmittelService;
import domain.betriebsmittelverwaltung.required.IBetriebsmittelHibernate;

public class BetriebsmittelModul extends AbstractModule {

	
	protected void configure(){
		bind(IBetriebsmittelService.class).to(BetriebsmittelServiceImpl.class);
		bind(IBetriebsmittelHibernate.class).to(BetriebsmittelHibernate.class);
		bind(BetriebsmittelHibernate.class).in(Singleton.class);
	}
	
	
}
