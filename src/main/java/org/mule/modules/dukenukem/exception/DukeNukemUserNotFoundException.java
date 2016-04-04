package org.mule.modules.dukenukem.exception;

public class DukeNukemUserNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public DukeNukemUserNotFoundException() {
	}

	public DukeNukemUserNotFoundException(String message) {
		super(message);
	}

	public DukeNukemUserNotFoundException(Throwable cause) {
		super(cause);
	}

	public DukeNukemUserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
