package main.modules;

import com.google.inject.AbstractModule;

import datenschicht.kundenauftragsverwaltung.KundenauftragHibernate;
import domain.kundenauftragsverwaltung.core.KundenauftragServiceImpl;
import domain.kundenauftragsverwaltung.provided.IKundenauftragService;
import domain.kundenauftragsverwaltung.required.IKundenauftragHibernate;

public class KundenauftragModul extends AbstractModule {

	protected void configure() {
		bind(IKundenauftragService.class).to(KundenauftragServiceImpl.class);
		bind(IKundenauftragHibernate.class).to(KundenauftragHibernate.class);
	}
	
}
