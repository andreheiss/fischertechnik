package domain.kundenauftragsverwaltung.provided;

import java.util.List;

import domain.kundenauftragsverwaltung.core.Kundenauftrag;
import domain.shared.Auftragstatus;

public interface IKundenauftragService {

	public boolean createKundenauftrag(Kundenauftrag ka);

	/**Get the orders according to the attribute values
	 * set in the object. Status can't be set. Therefore,
	 * you can use a separate method.
	 * @param ka Kundenauftrag
	 * @return List of Kundenauftrag
	 */
	public List<Kundenauftrag> getKundenauftrag(Kundenauftrag ka);

	/**Get the orders with the additional search criteria Auftragsstatus
	 * 
	 * @param ka Kundenauftrag
	 * @param a Auftragsstatus
	 * @return List of Kundenauftrag
	 */
	public List<Kundenauftrag> getKundenauftrag(Kundenauftrag ka, Auftragstatus a);

	public boolean changeKundenauftrag(Kundenauftrag ka);

	public boolean deleteKundenauftrag(Kundenauftrag ka);

	public boolean kundenauftragFreigeben(Kundenauftrag ka);

	public boolean kundenauftragStornieren(Kundenauftrag ka);
}
