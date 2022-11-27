package controller.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.FindingServiceException;
import entity.Role;
import entity.Service;
import entity.User;
import service.ServiceManager;

/**
 * Shows a list of all providing services
 * 
 * @author yevgenia.kovalova
 *
 */

public class ShowServiceListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowServiceListCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN, Role.CLIENT));
	public static final boolean IS_GUEST_ALLOWED = true;

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

		ServiceManager manager = ServiceManager.getInstance();
		List<Service> services = new ArrayList<>();

		try {
			services = manager.findAllservices();
		} catch (FindingServiceException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to get service list");
			return "/error.jsp";
		}

		request.setAttribute("servicelist", services);
		return "/service_list.jsp";
	}

}
