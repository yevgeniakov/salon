package controller.command.impl;

import java.sql.SQLException;
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
import entity.Service;
import entity.User;
import service.AppointmentManager;

public class CreateAppointmentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CreateAppointmentCommand.class);
	private static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.CLIENT));
	private static final boolean IS_GUEST_ALLOWED = false;

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
			int service_id = Integer.parseInt(request.getParameter("service_id"));
			int master_id = Integer.parseInt(request.getParameter("master_id"));
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			int timeslot = Integer.parseInt(request.getParameter("timeslot"));
			LocalDate date = LocalDate.parse(request.getParameter("date"));

			
			/*
			 * if (!ValidatorUtil.isValidName(name) || !ValidatorUtil.validPassword(pass) ||
			 * !ValidatorUtil.validEmail(email) || !ValidatorUtil.validFullname(fullname)) {
			 * request.setAttribute(PARAM_ERROR, MSG_INVALID_INPUT); return PAGE_REGISTER; }
			 */
			// getLogger().info(name + " " + pass + " " + email + " " + fullname);
			User user = new User();
			user.setId(user_id);

			User master = new User();
			master.setId(master_id);

			Service service = new Service();
			service.setId(service_id);

			AppointmentManager manager = AppointmentManager.getInstance();
			int sum = manager.getPriceByMasterAndService(master, service);

			Appointment appointment = new Appointment(date, timeslot, master, user, service, sum, false, false, "", 0);
			appointment = manager.createAppointment(appointment);

			/*
			 * if (!CommandUtil.setLoggedUser(request, user)) {
			 * request.setAttribute(PARAM_ERROR, MSG_ALREADY_LOGIN); return PAGE_LOGIN; }
			 */

			return "Controller?command=show_master_schedule&id=" + appointment.getMaster().getId() + "&date="
					+ appointment.getDate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "Something wrong. try once more.");

			return "/error.jsp";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "Something wrong. try once more.");
			
			return "/error.jsp";
		}
	}

}
