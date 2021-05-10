package main.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import datenschicht.teileverwaltung.TeileHibernate;
import domain.teileverwaltung.core.TeilServiceImpl;
import domain.teileverwaltung.providedservices.ITeilService;
import domain.teileverwaltung.requiredservices.ITeilHibernate;

public class TeilModul extends AbstractModule {

	protected void configure() {
		bind(ITeilService.class).to(TeilServiceImpl.class);
		bind(ITeilHibernate.class).to(TeileHibernate.class);
		bind(TeileHibernate.class).in(Singleton.class);
//		bind(ITeilHibernate.class).to(TeileRepository.class);
//		bind(TeileRepository.class).in(Singleton.class);
	}

	
}
