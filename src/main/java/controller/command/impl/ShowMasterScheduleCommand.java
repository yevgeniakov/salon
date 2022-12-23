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
import controller.exceptions.FindingUserException;
import controller.exceptions.IncorrectParamException;
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;
import service.UserManager;
import service.utils.ValidatorUtil;

/**
 * Shows a schedule of certain master for the certain day
 * 
 * @author yevgenia.kovalova
 *
 */
public class ShowMasterScheduleCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowMasterScheduleCommand.class);
	private UserManager userManager;
	private AppointmentManager appointmentManager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(List.of(Role.ADMIN, Role.CLIENT, Role.HAIRDRESSER));
	public static final boolean IS_GUEST_ALLOWED = false;

	public ShowMasterScheduleCommand(UserManager userManager, AppointmentManager appointmentManager) {
		this.userManager = userManager;
		this.appointmentManager = appointmentManager;
	}

	public ShowMasterScheduleCommand() {
		this.userManager = UserManager.getInstance();
		this.appointmentManager = AppointmentManager.getInstance();
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
		int id = 0;
		LocalDate date = null;
		try {
			id = ValidatorUtil.parseIntParameter(request.getParameter("id"));
			date = ValidatorUtil.parseDateParameter(request.getParameter("date"));
		} catch (IncorrectParamException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
		try {
			User master = userManager.findUserbyID(id);
			List<Appointment> appointments = appointmentManager.getMasterSchedule(date, master);
			request.setAttribute("schedule", appointments);
			request.setAttribute("master", master);
			request.setAttribute("id", id);
			request.setAttribute("date", date);
			return "/master_schedule.jsp";
		} catch (FindingUserException | FindingAppointmentException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
}
