package main.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import datenschicht.arbeitsplanverwaltung.ArbeitsgangHibernate;
import datenschicht.arbeitsplanverwaltung.ArbeitsplanHibernate;
import domain.arbeitsplanverwaltung.core.ArbeitsgangServiceImpl;
import domain.arbeitsplanverwaltung.core.ArbeitsplanServiceImpl;
import domain.arbeitsplanverwaltung.provided.IArbeitsgangService;
import domain.arbeitsplanverwaltung.provided.IArbeitsplanService;
import domain.arbeitsplanverwaltung.required.IArbeitsgangHibernate;
import domain.arbeitsplanverwaltung.required.IArbeitsplanHibernate;


public class ArbeitsplanModul extends AbstractModule{
	
	protected void configure() {
		bind(IArbeitsgangService.class).to(ArbeitsgangServiceImpl.class);
		bind(IArbeitsplanService.class).to(ArbeitsplanServiceImpl.class);
		bind(IArbeitsgangHibernate.class).to(ArbeitsgangHibernate.class);
		bind(ArbeitsgangHibernate.class).in(Singleton.class);
		bind(IArbeitsplanHibernate.class).to(ArbeitsplanHibernate.class);
		bind(ArbeitsplanHibernate.class).in(Singleton.class);
	}
}
