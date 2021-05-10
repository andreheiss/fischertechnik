package domain.teileverwaltung.providedservices;

import java.util.List;

import domain.teileverwaltung.core.InvalidTeilException;
import domain.teileverwaltung.core.Teil;

public interface ITeilService {
	
	/**
	 * For storing a Teil.
	 * @param t Teil to save
	 * @return true if storing was successful.
	 */
	
	public boolean createTeil(Teil t) throws InvalidTeilException;
	/**
	 * For retrieving a single or muliple Object in form of a list
	 * depending.
	 * @param t Teil with search-criterias in the attributes.
	 * @return a list of the type Teil.
	 */
	public List<Teil> getTeil(Teil t);
	
	/**
	 * For changing or delimiting a Teil.
	 * @param t a Teil with the new attribute values.
	 * @return true if changing was successful.
	 */
	public boolean changeTeil(Teil t) throws InvalidTeilException;
	
	/**
	 * For deleting a Teil.
	 * @param t the specific Teil which should be deleted.
	 * @return true if deleting was successful.
	 */
	public boolean deleteTeil(Teil t);

}
