package controller.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.FindingUserException;
import controller.exceptions.IncorrectParamException;
import entity.Role;
import entity.Service;
import entity.User;
import service.ServiceManager;
import service.UserManager;
import service.utils.ValidatorUtil;

public class ShowUserInfoCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowUserInfoCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN, Role.CLIENT, Role.HAIRDRESSER));
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

		int id = 0;

		if (!(request.getParameter("id") == null)) {
			try {
				id = ValidatorUtil.parseIntParameter(request.getParameter("id"));
			} catch (IncorrectParamException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("error", e.getMessage());
				return "/error.jsp";
			}
		} else {
				
			if (!(loggedUser == null)) {
				id = loggedUser.getId();
			}
		}

		if (id == 0) {
			logger.error("unable to find user");
			request.setAttribute("error", "unable to find user!");
			return "/error.jsp";
		}

		UserManager userManager = UserManager.getInstance();
		User user = new User();
		try {
			user = userManager.findUserbyID(id);
		} catch (FindingUserException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to find user!");
			return "/error.jsp";
		}
		
		if (!(user.getRole() == Role.HAIRDRESSER 
				|| (loggedUser != null
						&& (loggedUser.getId() == user.getId() || loggedUser.getRole() != Role.CLIENT)))) {
			logger.error("Access denied", loggedUser, user.getId());
			request.setAttribute("error", "You are not allowed to see this user info!");
			return "/error.jsp";
		}
		

		ServiceManager serviceManager = ServiceManager.getInstance();
		TreeMap<Service, Integer> services = new TreeMap<>();
		if (user.getRole() == Role.HAIRDRESSER) {
			services = new TreeMap<>();
			try {
				logger.debug("start to find all services for master");
				services = serviceManager.findAllServicesByMaster(user.getId());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("error", e.getMessage());
				return "/error.jsp";
			}
		} 

		request.setAttribute("services", services);
		request.setAttribute("showuser", user);
		return "/user_info.jsp";
	}

}
