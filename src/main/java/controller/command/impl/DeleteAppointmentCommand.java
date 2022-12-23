package controller.command.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.DeletingAppointmentException;
import controller.exceptions.IncorrectParamException;
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;
import service.utils.ValidatorUtil;

/**
 * Deletes an appointment (Admin only)
 * 
 * @author yevgenia.kovalova
 *
 */

public class DeleteAppointmentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(DeleteAppointmentCommand.class);
	private AppointmentManager manager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(List.of(Role.ADMIN));
	public static final boolean IS_GUEST_ALLOWED = false;

	public DeleteAppointmentCommand(AppointmentManager manager) {
		this.manager = manager;
	}

	public DeleteAppointmentCommand() {
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
			User master = new User();
			master.setId(master_id);
			Appointment appointment = new Appointment(date, timeslot, master, null, null, 0, false, false, "", 0);
			manager.deleteAppointment(appointment);
			request.setAttribute("message", "appointment deleted");
			logger.info("appointment deleted", master_id, date, timeslot);
			request.setAttribute("redirect", "redirect");
			return "Controller?command=show_master_schedule&id=" + master_id + "&date=" + date;
		} catch (DeletingAppointmentException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
}
