package domain.produktionsplanung.required;

import domain.produktionsplanung.core.Fertigungsarbeitsplan;

public interface IGetArbeitsplan {

	public Fertigungsarbeitsplan getArbeitsplanForTeil(int id);
}
