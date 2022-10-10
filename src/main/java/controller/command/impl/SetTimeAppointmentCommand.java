package controller.command.impl;

import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;

public class SetTimeAppointmentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(SetTimeAppointmentCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}

		try {
			LocalDate date = (request.getParameter("date") == null || request.getParameter("date").equals("null"))
					? null
					: LocalDate.parse(request.getParameter("date"));
			LocalDate newDate = (request.getParameter("newdate") == null
					|| request.getParameter("newdate").equals("null")) ? null
							: LocalDate.parse(request.getParameter("newdate"));
			Integer master_id = (request.getParameter("master_id") == null
					|| request.getParameter("master_id").equals("null")) ? null
							: Integer.parseInt(request.getParameter("master_id"));
			Integer timeslot = (request.getParameter("timeslot") == null
					|| request.getParameter("timeslot").equals("null")) ? null
							: Integer.parseInt(request.getParameter("timeslot"));
			Integer newTimeslot = (request.getParameter("newtimeslot") == null
					|| request.getParameter("newtimeslot").equals("null")) ? null
							: Integer.parseInt(request.getParameter("newtimeslot"));

			if (loggedUser.getRole() != Role.ADMIN) {
				logger.info("unauthorized access. Redirecting to index page");
				return "/index.jsp";
			}

			// TODO Validator

			Appointment appointment = null;
			AppointmentManager manager = AppointmentManager.getInstance();
			appointment = manager.findAppointmentByKey(master_id, date, timeslot);
			manager.setTimeAppointment(appointment, newDate, newTimeslot);
			appointment = manager.findAppointmentByKey(master_id, newDate, newTimeslot);
			return "Controller?command=show_master_schedule&id=" + appointment.getMaster().getId() + "&date="
					+ appointment.getDate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
