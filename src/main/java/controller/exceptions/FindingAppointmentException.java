package controller.exceptions;

public class FindingAppointmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FindingAppointmentException(String errorMessage) {
        super(errorMessage);
    }
}

