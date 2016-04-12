package org.mule.modules.dukenukem.exception;

public class DukeNukemServerErrorException extends Exception{

	private static final long serialVersionUID = 1L;

	public DukeNukemServerErrorException() {
	}

	public DukeNukemServerErrorException(String message) {
		super(message);
	}

	public DukeNukemServerErrorException(Throwable cause) {
		super(cause);
	}

	public DukeNukemServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
