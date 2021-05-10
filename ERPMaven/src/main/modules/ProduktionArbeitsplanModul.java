package main.modules;

import com.google.inject.AbstractModule;

import domain.mapping.ProduktionArbeitsplanAdapter;
import domain.produktionsplanung.required.IGetArbeitsplan;

public class ProduktionArbeitsplanModul extends AbstractModule{

	public void configure() {
		bind(IGetArbeitsplan.class).to(ProduktionArbeitsplanAdapter.class);
	}
}
