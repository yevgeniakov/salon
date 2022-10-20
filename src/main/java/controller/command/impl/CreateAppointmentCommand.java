package controller.command.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.CreatingAppointmentException;
import controller.exceptions.FindingAppointmentException;
import controller.exceptions.IncorrectParamException;
import entity.Appointment;
import entity.Role;
import entity.Service;
import entity.User;
import service.AppointmentManager;
import service.utils.ValidatorUtil;

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
			logger.info("Access denied.", loggedUser,
					loggedUser == null ? "GUEST" : loggedUser.getRole());
			
			request.setAttribute("error", "Access denied");
			return "/error.jsp";
		}

		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
			int service_id = 0;
			int master_id = 0;
			int user_id = 0;
			int timeslot = 0;
			LocalDate date = null;
			try {
				service_id = ValidatorUtil.parseIntParameter(request.getParameter("service_id"));
				master_id = ValidatorUtil.parseIntParameter(request.getParameter("master_id"));
				user_id = ValidatorUtil.parseIntParameter(request.getParameter("user_id"));
				timeslot = ValidatorUtil.parseTimeslotParameter(request.getParameter("timeslot"));
				date = ValidatorUtil.parseDateParameter(request.getParameter("date"));
			} catch (IncorrectParamException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("error", e.getMessage());
				return "/error.jsp";
			}
			if (date.isBefore(LocalDate.now())) {
				logger.error("try to make appointment in the past");
				request.setAttribute("error", "You are trying to make appointment in the past. Please, choose the correct date.");
				return "/error.jsp";
			}
			try {
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

			logger.info("new appointment created", master_id, user_id, service_id, date, timeslot);
			request.setAttribute("redirect", "redirect");
			return "Controller?command=show_master_schedule&id=" + appointment.getMaster().getId() + "&date="
					+ appointment.getDate();
		} catch (FindingAppointmentException | CreatingAppointmentException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "Something wrong. try once more.");
			return "/error.jsp";
		}
	}

}
