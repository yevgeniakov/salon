package controller.exceptions;

public class CreatingAppointmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreatingAppointmentException(String errorMessage) {
        super(errorMessage);
    }
}
