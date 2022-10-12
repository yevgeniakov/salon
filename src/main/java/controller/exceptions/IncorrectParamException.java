package controller.exceptions;

public class IncorrectParamException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncorrectParamException(String errorMessage) {
        super(errorMessage);
    }

}
