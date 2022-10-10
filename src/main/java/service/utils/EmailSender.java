package service.utils;

import java.time.LocalDate;
import java.util.List;

import entity.Appointment;
import service.AppointmentManager;

public class EmailSender implements Runnable {

    @Override
    public void run() {
		/*
		 * AppointmentManager manager = AppointmentManager.getInstance();
		 * manager.sendEmailsForFeedback();
		 */
    }
    
    
    public void sendEmailsForFeedback() {
		LocalDate dateFrom = LocalDate.now().minusDays(1);
		LocalDate dateTo = dateFrom;
		AppointmentManager manager = AppointmentManager.getInstance();
		List<Appointment> appointments;
		try {
			appointments = manager.findAppointmentsByConditions(dateFrom, dateTo, null, null, null, true, null, false);

			for (Appointment appointment : appointments) {
				String subject = "Dear " + appointment.getUser().getName() + appointment.getUser().getSurname()
						+ ", we are waiting for your feedback!";
				String body = "Hello! It's Beauty Salon. Please, provide your feedback for the appointment";

				MailUtil.sendMessage(appointment.getUser().getEmail(), subject, body);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
