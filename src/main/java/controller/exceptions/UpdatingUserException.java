package controller.exceptions;

public class UpdatingUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdatingUserException(String errorMessage) {
		super(errorMessage);
	}
}
