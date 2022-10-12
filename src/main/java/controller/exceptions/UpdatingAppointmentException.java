package controller.exceptions;

public class UpdatingAppointmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdatingAppointmentException(String errorMessage) {
		super(errorMessage);
	}
}
