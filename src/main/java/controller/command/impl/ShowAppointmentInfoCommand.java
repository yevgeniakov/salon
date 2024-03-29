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
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;
import service.utils.ValidatorUtil;

/**
 * Shows detailed info for the appointment
 * 
 * @author yevgenia.kovalova
 *
 */

public class ShowAppointmentInfoCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowAppointmentInfoCommand.class);
	private AppointmentManager manager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(List.of(Role.ADMIN, Role.CLIENT, Role.HAIRDRESSER));
	public static final boolean IS_GUEST_ALLOWED = false;

	public ShowAppointmentInfoCommand(AppointmentManager manager) {
		this.manager = manager;
	}

	public ShowAppointmentInfoCommand() {
		this.manager = AppointmentManager.getInstance();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");
		if (!commandIsAllowed(loggedUser, ROLES_ALLOWED, IS_GUEST_ALLOWED)) {
			logger.info("Access denied.", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
			request.setAttribute("error", "Access denied");
			return "/error.jsp";
		}
		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
		int master_id = 0;
		int timeslot = 0;
		LocalDate date = null;
		try {
			master_id = ValidatorUtil.parseIntParameter(request.getParameter("master_id"));
			timeslot = ValidatorUtil.parseTimeslotParameter(request.getParameter("timeslot"));
			date = ValidatorUtil.parseDateParameter(request.getParameter("date"));
		} catch (IncorrectParamException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
		try {
			Appointment appointment = new Appointment();
			appointment = manager.findAppointmentByKey(master_id, date, timeslot);
			if (appointment == null || (!appointment.getUser().equals(loggedUser)
					&& !appointment.getMaster().equals(loggedUser) && loggedUser.getRole() != Role.ADMIN)) {
				logger.error("access denied", loggedUser, appointment);
				request.setAttribute("error", "You are not allowed to see view this appointment info!");
				return "/error.jsp";
			}
			request.setAttribute("appointment", appointment);
			request.setAttribute("master_id", master_id);
			request.setAttribute("date", date);
			request.setAttribute("timeslot", timeslot);
			return "/appointment_info.jsp";
		} catch (FindingAppointmentException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
}
