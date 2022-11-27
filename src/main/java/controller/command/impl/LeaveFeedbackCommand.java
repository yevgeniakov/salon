package controller.command.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.FindingAppointmentException;
import controller.exceptions.IncorrectParamException;
import controller.exceptions.UpdatingAppointmentException;
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;
import service.utils.ValidatorUtil;

/**
 * Leaves a feedback for the appointment (Client only)
 * 
 * @author yevgenia.kovalova
 *
 */

public class LeaveFeedbackCommand implements Command {
	private static final Logger logger = LogManager.getLogger(LeaveFeedbackCommand.class);
	private AppointmentManager manager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.CLIENT));
	public static final boolean IS_GUEST_ALLOWED = false;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");
		if (!commandIsAllowed(loggedUser, ROLES_ALLOWED, IS_GUEST_ALLOWED)) {
			logger.info("Access denied.", loggedUser,
					loggedUser == null ? "GUEST" : loggedUser.getRole());
			
			request.setAttribute("error", "Access denied");
			return "/error.jsp";
		}
		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
			int master_id = 0;
			int timeslot = 0;
			LocalDate date = null;
			int rating = 0;
			try {
				master_id = ValidatorUtil.parseIntParameter(request.getParameter("master_id"));
				timeslot = ValidatorUtil.parseTimeslotParameter(request.getParameter("timeslot"));
				date = ValidatorUtil.parseDateParameter(request.getParameter("date"));
				rating = ValidatorUtil.parseRatingParameter(request.getParameter("rating"));
			} catch (IncorrectParamException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("error", e.getMessage());
				return "/error.jsp";
			}
			String feedback = request.getParameter("feedback");
			try {
			//AppointmentManager manager = AppointmentManager.getInstance();

			Appointment appointment = manager.findAppointmentByKey(master_id, date, timeslot);

			if (!appointment.getUser().equals(loggedUser) || !appointment.getIsDone()) {
				logger.info("try to leave feedback. Not allowed.");
				request.setAttribute("error", "You are not allowed to leave feedback for this appointment!");
				return "/error.jsp";
			}
			manager.setFeedbackForAppointmentAndUpdateMaster(appointment, rating, feedback);
			logger.info("added feedback for appointment", appointment.getMaster().getId(), appointment.getDate(), appointment.getTimeslot());
			request.setAttribute("redirect", "redirect");
			return "Controller?command=show_appointment_info&master_id=" + appointment.getMaster().getId() + "&date=" + appointment.getDate() + "&timeslot=" + appointment.getTimeslot();
		} catch (FindingAppointmentException | UpdatingAppointmentException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
	public LeaveFeedbackCommand(AppointmentManager manager) {
		this.manager = manager;
	}
	public LeaveFeedbackCommand() {
		this.manager = AppointmentManager.getInstance();
	}
}
