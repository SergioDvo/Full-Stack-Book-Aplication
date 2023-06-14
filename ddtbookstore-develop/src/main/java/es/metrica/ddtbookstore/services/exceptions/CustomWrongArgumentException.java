package es.metrica.ddtbookstore.services.exceptions;

public class CustomWrongArgumentException extends RuntimeException {
	public CustomWrongArgumentException(String message) {
		super(message);
	}
}
