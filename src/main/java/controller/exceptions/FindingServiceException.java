package controller.exceptions;

public class FindingServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FindingServiceException(String errorMessage) {
        super(errorMessage);
    }
}
