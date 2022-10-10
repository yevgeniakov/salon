package controller.command.impl;

import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Appointment;
import entity.User;
import service.AppointmentManager;

public class ShowAppointmentInfoCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowAppointmentInfoCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}

		try {
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			int timeslot = Integer.parseInt(request.getParameter("timeslot"));
			int master_id = Integer.parseInt(request.getParameter("master_id"));

			AppointmentManager manager = AppointmentManager.getInstance();
			Appointment appointment = new Appointment();
			appointment = manager.findAppointmentByKey(master_id, date, timeslot);

			request.setAttribute("appointment", appointment);
			request.setAttribute("master_id", master_id);
			request.setAttribute("date", date);
			request.setAttribute("timeslot", timeslot);

			return "/appointment_info.jsp";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
