package controller.exceptions;

public class CreatingServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreatingServiceException(String errorMessage) {
        super(errorMessage);
    }
}
