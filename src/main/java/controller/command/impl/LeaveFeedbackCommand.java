package controller.command.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Appointment;
import entity.User;
import service.AppointmentManager;

public class LeaveFeedbackCommand implements Command {
	private static final Logger logger = LogManager.getLogger(LeaveFeedbackCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}

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
			return "Controller?command=show_master_schedule&id=" + appointment.getMaster().getId() + "&date="
					+ appointment.getDate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
}
