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

public class SetTimeAppointmentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(SetTimeAppointmentCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN));
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

		
			// TODO Validator

			Appointment appointment = null;
			AppointmentManager manager = AppointmentManager.getInstance();
			appointment = manager.findAppointmentByKey(master_id, date, timeslot);
			manager.setTimeAppointment(appointment, newDate, newTimeslot);
			appointment = manager.findAppointmentByKey(master_id, newDate, newTimeslot);
			logger.info("set new datetime for appointment", appointment.getMaster().getId(), appointment.getDate(), appointment.getTimeslot());
			return "Controller?command=show_master_schedule&id=" + appointment.getMaster().getId() + "&date="
					+ appointment.getDate();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
