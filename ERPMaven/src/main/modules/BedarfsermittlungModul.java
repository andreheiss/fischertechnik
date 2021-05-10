package main.modules;

import com.google.inject.AbstractModule;

import domain.bedarfsplanung.core.BedarfsermittlungServiceImpl;
import domain.bedarfsplanung.provided.IBedarfsermittlungService;

public class BedarfsermittlungModul extends AbstractModule {

	
	protected void configure() {
		bind(IBedarfsermittlungService.class).to(BedarfsermittlungServiceImpl.class);
		
	}
}
