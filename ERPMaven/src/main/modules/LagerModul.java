package main.modules;

import com.google.inject.AbstractModule;

import datenschicht.lagerverwaltung.LagerHibernate;
import domain.lagerverwaltung.core.LagerServiceImpl;
import domain.lagerverwaltung.provided.ILagerService;
import domain.lagerverwaltung.required.ILagerHibernate;

public class LagerModul extends AbstractModule {

	protected void configure() {
		bind(ILagerService.class).to(LagerServiceImpl.class);
		bind(ILagerHibernate.class).to(LagerHibernate.class);
	}
}
