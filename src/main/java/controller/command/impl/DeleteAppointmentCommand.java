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

public class DeleteAppointmentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(DeleteAppointmentCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}

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
			return "Controller?command=show_master_schedule&id=" + master_id + "&date=" + date;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
