package service.utils;

import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.impl.ShowAppointmentInfoCommand;
import controller.exceptions.FindingAppointmentException;
import entity.Appointment;
import service.AppointmentManager;

/**
 * Tool for sending scheduled emails
 * 
 * @author yevgenia.kovalova
 *
 */

public class EmailSender implements Runnable {
	private static final Logger logger = LogManager.getLogger(ShowAppointmentInfoCommand.class);

	@Override
	public void run() {
		 // sendEmailsForFeedback();
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
		} catch (FindingAppointmentException | MessagingException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
