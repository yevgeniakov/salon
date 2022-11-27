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
 * Marks the appointment as paid/unpaid (Admin only)
 * 
 * @author yevgenia.kovalova
 *
 */

public class SetPayAppointmentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(SetPayAppointmentCommand.class);
	private AppointmentManager manager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(List.of(Role.ADMIN));
	public static final boolean IS_GUEST_ALLOWED = false;

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
			Appointment appointment = manager.findAppointmentByKey(master_id, date, timeslot);
			manager.setPayAppointment(appointment, !appointment.getIsPaid());
			appointment.setIsPaid(!appointment.getIsPaid());

			logger.info("set appointment payment", appointment.getIsPaid(), appointment.getMaster().getId(),
					appointment.getDate(), appointment.getTimeslot());
			request.setAttribute("redirect", "redirect");
			return "Controller?command=show_appointment_info&master_id=" + appointment.getMaster().getId() + "&date=" + appointment.getDate() + "&timeslot=" + appointment.getTimeslot();
		} catch (FindingAppointmentException | UpdatingAppointmentException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
	public SetPayAppointmentCommand(AppointmentManager manager) {
		this.manager = manager;
	}
	public SetPayAppointmentCommand() {
		this.manager = AppointmentManager.getInstance();
	}
}
