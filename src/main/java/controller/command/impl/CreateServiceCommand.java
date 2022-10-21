package controller.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.CreatingServiceException;
import entity.Role;
import entity.Service;
import entity.User;
import service.ServiceManager;
import service.utils.ValidatorUtil;

public class CreateServiceCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CreateServiceCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN));
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
			String name = request.getParameter("name");
			String info = request.getParameter("info");
			if (!ValidatorUtil.isValidText(name) || !ValidatorUtil.isValidText(info)) {
				logger.error("Service is not created. Invalid params", name, info);
				request.setAttribute("error", "Can't create service. Invalid input data.");
				return "/error.jsp";
			}
			Service service = new Service(0, name, info);
			ServiceManager manager = ServiceManager.getInstance();
			service = manager.createService(service);

			if (service.getId() == 0) {
				logger.error("Service is not created");
				request.setAttribute("error", "Can't create service");
				return "/error.jsp";	
			}
			logger.info("new service created", service.getId(), name);
			request.setAttribute("redirect", "redirect");
			return "Controller?command=show_service_list";
		} catch (CreatingServiceException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
