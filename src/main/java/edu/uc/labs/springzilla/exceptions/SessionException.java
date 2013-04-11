package edu.uc.labs.springzilla.exceptions;

public class SessionException extends RuntimeException {
	
	public SessionException(String reason){
		super(reason);
	}
}
