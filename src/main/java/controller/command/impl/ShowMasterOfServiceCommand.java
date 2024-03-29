package controller.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
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

/**
 * Shows masters, that provide a certain service
 * 
 * @author yevgenia.kovalova
 *
 */
public class ShowMasterOfServiceCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowMasterOfServiceCommand.class);
	private UserManager userManager;
	private ServiceManager serviceManager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(List.of(Role.ADMIN, Role.CLIENT));
	public static final boolean IS_GUEST_ALLOWED = true;

	public ShowMasterOfServiceCommand(UserManager userManager, ServiceManager serviceManager) {
		this.userManager = userManager;
		this.serviceManager = serviceManager;
	}

	public ShowMasterOfServiceCommand() {
		this.userManager = UserManager.getInstance();
		this.serviceManager = ServiceManager.getInstance();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");
		if (!commandIsAllowed(loggedUser, ROLES_ALLOWED, IS_GUEST_ALLOWED)) {
			logger.info("Access denied.", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
			request.setAttribute("error", "Access denied");
			return "/error.jsp";
		}
		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
		SortedMap<User, Integer> masters = null;
		String sort = request.getParameter("sort");
		int service_id = 0;
		try {
			service_id = ValidatorUtil.parseIntParameter(request.getParameter("service_id"));
		} catch (IncorrectParamException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
		try {
			masters = userManager.findAllMastersByService(service_id, sort);
		} catch (FindingUserException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to get master list");
			return "/error.jsp";
		}
		Service service = new Service();
		try {
			service = serviceManager.findServiceByID(service_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to obtain service by ID");
			return "/error.jsp";
		}
		request.setAttribute("masters", masters);
		request.setAttribute("sort", sort);
		request.setAttribute("service_id", service_id);
		request.setAttribute("service", service);
		return "/master_list.jsp";
	}
}
