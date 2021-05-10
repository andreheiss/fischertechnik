package domain.geschaeftspartnerverwaltung.required;

import java.util.List;

import domain.geschaeftspartnerverwaltung.core.Geschaeftspartner;

public interface IGeschaeftspartnerHibernate {

	public boolean createGeschaeftspartner(Geschaeftspartner gs);

	public List<Geschaeftspartner> getGeschaeftspartner(Geschaeftspartner gs);

	public boolean changeGeschaeftspartner(Geschaeftspartner gs);

	public boolean deleteGeschaeftspartner(Geschaeftspartner gs);

}
