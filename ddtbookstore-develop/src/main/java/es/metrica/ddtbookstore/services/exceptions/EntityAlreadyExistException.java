package es.metrica.ddtbookstore.services.exceptions;

public class EntityAlreadyExistException extends RuntimeException {
	public EntityAlreadyExistException(String message) {
		super(message);
	}
}
