package org.elis.progettoesempio.exception;

public class ResourceNotFoundException extends RuntimeException {
	 
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s non trovato/a con id: %d", resourceName, id));
    }
 
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
 