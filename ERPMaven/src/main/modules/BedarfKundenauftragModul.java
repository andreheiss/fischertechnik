package main.modules;

import com.google.inject.AbstractModule;

import domain.bedarfsplanung.required.IGetKundenauftrag;
import domain.mapping.BedarfKundenauftragAdapter;

public class BedarfKundenauftragModul extends AbstractModule {

	protected void configure() {
		bind(IGetKundenauftrag.class).to(BedarfKundenauftragAdapter.class);
	}
	
	
}
