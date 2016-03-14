package org.mule.modules.dukenukem.exception;

public class DukeNukemBusinessException extends Exception{

	private static final long serialVersionUID = 1L;

	public DukeNukemBusinessException() {
	}

	public DukeNukemBusinessException(String message) {
		super(message);
	}

	public DukeNukemBusinessException(Throwable cause) {
		super(cause);
	}

	public DukeNukemBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
