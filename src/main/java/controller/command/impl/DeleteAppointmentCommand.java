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

public class DeleteAppointmentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(DeleteAppointmentCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN));
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

			User master = new User();
			master.setId(master_id);

			Appointment appointment = new Appointment(date, timeslot, master, null, null, 0, false, false, "", 0);
			AppointmentManager manager = AppointmentManager.getInstance();
			manager.deleteAppointment(appointment);
			request.setAttribute("message", "appointment deleted");
			logger.info("appointment deleted", master_id, date, timeslot);
			return "Controller?command=show_master_schedule&id=" + master_id + "&date=" + date;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
