package controller.exceptions;

public class CheckCredentialsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
