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
import service.UserManager;

public class ShowMasterScheduleCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowMasterScheduleCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN, Role.CLIENT, Role.HAIRDRESSER));
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
			int id = Integer.parseInt(request.getParameter("id"));
			LocalDate date = LocalDate.parse(request.getParameter("date"));

			UserManager userManager = UserManager.getInstance();
			User master = userManager.findUserbyID(id);

			AppointmentManager manager = AppointmentManager.getInstance();
			List<Appointment> appointments = manager.getMasterSchedule(date, master);
			request.setAttribute("schedule", appointments);
			request.setAttribute("master", master);
			request.setAttribute("id", id);
			request.setAttribute("date", date);
			return "/master_schedule.jsp";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
