package controller.exceptions;

public class CreatingUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreatingUserException(String errorMessage) {
        super(errorMessage);
    }
}
