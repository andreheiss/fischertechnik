package domain.kundenauftragsverwaltung.required;

import domain.kundenauftragsverwaltung.core.Kundenauftrag;

public interface IBedarfsermittlung {

	public void bedarfsermittlungDurchfuehren(Kundenauftrag ka, boolean sim);
}
