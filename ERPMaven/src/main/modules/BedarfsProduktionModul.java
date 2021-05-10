package main.modules;

import com.google.inject.AbstractModule;

import domain.bedarfsplanung.required.ITerminierung;
import domain.mapping.BedarfProduktionAdapter;

public class BedarfsProduktionModul extends AbstractModule{

	protected void configure() {
		bind(ITerminierung.class).to(BedarfProduktionAdapter.class);
	}
	
}
