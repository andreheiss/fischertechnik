package domain.arbeitsplanverwaltung.core;

public class InvalidArbeitsgangException extends Exception {

	public InvalidArbeitsgangException(String error) {
		super(error);
	}
}
