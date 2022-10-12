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
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;

public class ShowAppointmentsListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowAppointmentsListCommand.class);
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
			LocalDate dateFrom = (request.getParameter("datefrom") == null
					|| request.getParameter("datefrom").equals("null")) ? LocalDate.now().minusDays(7)
							: LocalDate.parse(request.getParameter("datefrom"));
			LocalDate dateTo = (request.getParameter("dateto") == null || request.getParameter("dateto").equals("null"))
					? LocalDate.now().plusDays(7)
					: LocalDate.parse(request.getParameter("dateto"));
			Integer service_id = (request.getParameter("service_id") == null
					|| request.getParameter("service_id").equals("null")) ? null
							: Integer.parseInt(request.getParameter("service_id"));
			Integer master_id = (request.getParameter("master_id") == null
					|| request.getParameter("master_id").equals("null")) ? null
							: Integer.parseInt(request.getParameter("master_id"));
			Integer user_id = (request.getParameter("user_id") == null
					|| request.getParameter("user_id").equals("null")) ? null
							: Integer.parseInt(request.getParameter("user_id"));
			Boolean isPaid = (request.getParameter("ispaid") == null || request.getParameter("ispaid").equals("null"))
					? null
					: Boolean.parseBoolean(request.getParameter("ispaid"));
			Boolean isDone = (request.getParameter("isdone") == null || request.getParameter("isdone").equals("null"))
					? null
					: Boolean.parseBoolean(request.getParameter("isdone"));
			Boolean isRating = (request.getParameter("israting") == null
					|| request.getParameter("israting").equals("null")) ? null
							: Boolean.parseBoolean(request.getParameter("israting"));
			int itemsPerPage = (request.getParameter("itemsperpage") == null
					|| request.getParameter("itemsperpage").equals("null")) ? 10
							: Integer.parseInt(request.getParameter("itemsperpage"));
			int page = (request.getParameter("page") == null || request.getParameter("page").equals("null")) ? 1
					: Integer.parseInt(request.getParameter("page"));

			if (loggedUser.getRole() == Role.CLIENT) {
				user_id = loggedUser.getId();
			}

			if (loggedUser.getRole() == Role.HAIRDRESSER) {
				master_id = loggedUser.getId();
			}

			AppointmentManager manager = AppointmentManager.getInstance();
			List<Appointment> appointmentsList = new ArrayList<>();
			appointmentsList = manager.findAppointmentsByConditions(dateFrom, dateTo, master_id, user_id, service_id,
					isDone, isPaid, isRating);

			//// pagination

			int itemsAmount = appointmentsList.size();
			int pagesTotal = itemsAmount / itemsPerPage;
			pagesTotal = (pagesTotal * itemsPerPage == itemsAmount ? pagesTotal : pagesTotal + 1);
			int indexTo = itemsPerPage * page;
			int indexFrom = indexTo - itemsPerPage;
			List<Appointment> subAppointments = appointmentsList.subList(indexFrom, Math.min(indexTo, itemsAmount));

			request.setAttribute("appointmentsList", subAppointments);
			request.setAttribute("page", page);
			request.setAttribute("pagesTotal", pagesTotal);
			request.setAttribute("itemsPerPage", itemsPerPage);
			request.setAttribute("master_id", master_id);
			request.setAttribute("user_id", user_id);
			request.setAttribute("service_id", service_id);
			request.setAttribute("isPaid", isPaid);
			request.setAttribute("isRating", isRating);
			request.setAttribute("isDone", isDone);
			request.setAttribute("datefrom", dateFrom);
			request.setAttribute("dateto", dateTo);

			return "/appointments_list.jsp";
		} catch (FindingAppointmentException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
