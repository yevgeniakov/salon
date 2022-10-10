package controller.command.impl;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Appointment;
import entity.User;
import service.AppointmentManager;
import service.UserManager;

public class ShowMasterScheduleCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowMasterScheduleCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}

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
