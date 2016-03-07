package org.mule.modules.dukenukem.exception;

public class DukeNukemConnectorException extends Exception{

	private static final long serialVersionUID = 1L;

	public DukeNukemConnectorException() {
	}

	public DukeNukemConnectorException(String message) {
		super(message);
	}

	public DukeNukemConnectorException(Throwable cause) {
		super(cause);
	}

	public DukeNukemConnectorException(String message, Throwable cause) {
		super(message, cause);
	}
}
