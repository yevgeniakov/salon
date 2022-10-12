package controller.exceptions;

public class FindingUserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FindingUserException(String errorMessage) {
        super(errorMessage);
    }
}
