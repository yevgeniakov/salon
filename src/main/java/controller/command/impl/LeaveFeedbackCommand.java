package controller.command.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;

public class LeaveFeedbackCommand implements Command {
	private static final Logger logger = LogManager.getLogger(LeaveFeedbackCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.CLIENT));
	public static final boolean IS_GUEST_ALLOWED = false;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");
		if (!commandIsAllowed(loggedUser, ROLES_ALLOWED, IS_GUEST_ALLOWED)) {
			logger.info("Access denied. returning to index page", loggedUser,
					loggedUser == null ? "GUEST" : loggedUser.getRole());
			
			return "/index.jsp";
		}

		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());

		try {
			int master_id = Integer.parseInt(request.getParameter("master_id"));
			int timeslot = Integer.parseInt(request.getParameter("timeslot"));
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			double rating = Double.parseDouble(request.getParameter("rating"));
			String feedback = request.getParameter("feedback");

			AppointmentManager manager = AppointmentManager.getInstance();

			Appointment appointment = manager.findAppointmentByKey(master_id, date, timeslot);

			if (!appointment.getUser().equals(loggedUser) || !appointment.getIsDone()) {
				logger.info("try to leave feedback. Not allowed.");
				request.setAttribute("error", "You are not allowed to leave feedback for this appointment!");
				return "/error.jsp";
			}

			manager.setFeedbackForAppointmentandUpdateMaster(appointment, rating, feedback);
			logger.info("added feedback for appointment", appointment.getMaster().getId(), appointment.getDate(), appointment.getTimeslot());
			return "Controller?command=show_master_schedule&id=" + appointment.getMaster().getId() + "&date="
					+ appointment.getDate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
}
