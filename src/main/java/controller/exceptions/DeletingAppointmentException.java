package controller.exceptions;

public class DeletingAppointmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeletingAppointmentException(String errorMessage) {
        super(errorMessage);
    }
}

