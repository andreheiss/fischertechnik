package domain.produktionsplanung.core;

import com.google.inject.Inject;

import domain.produktionsplanung.provided.ITerminierService;

public class TerminierServiceImpl implements ITerminierService {

	private Terminierer term;

	@Inject
	public TerminierServiceImpl(Terminierer t) {
		term = t;
	}

	@Override
	public Produktionsauftrag vortwaertsTerminieren(Produktionsauftrag fa) {
		return term.vorwaertsTerminieren(fa);
	}

	@Override
	public Produktionsauftrag rueckwaertsTerminieren(Produktionsauftrag fa) {
		return term.rueckwaertsTerminieren(fa);
	}

}
