package main.modules;

import com.google.inject.AbstractModule;

import domain.produktionsplanung.core.TerminierServiceImpl;
import domain.produktionsplanung.provided.ITerminierService;

public class ProduktionsplanungModul extends AbstractModule{

	protected void configure() {
		bind(ITerminierService.class).to(TerminierServiceImpl.class);
	}
}
