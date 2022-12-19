package controller.command.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.FindingUserException;
import entity.Role;
import entity.User;
import service.UserManager;

/**
 * Shows a list of users with the Hairdresser role
 * 
 * @author yevgenia.kovalova
 *
 */
public class ShowMasterListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowMasterListCommand.class);
	private UserManager manager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN, Role.CLIENT));
	public static final boolean IS_GUEST_ALLOWED = true;
	
 	public ShowMasterListCommand(UserManager manager) {
		this.manager = manager;
	}
 	public ShowMasterListCommand() {
		this.manager = UserManager.getInstance();
	}

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
		String sort = request.getParameter("sort");
		List<User> users = new ArrayList<>();
		try {
			users = manager.findAllMasters();
		} catch (FindingUserException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to get master list");
			return "/error.jsp";
		}
		if (sort == null || "".equals(sort) || "surname".equals(sort)) {
			users.sort(Comparator.comparing(User::getSurname));
		}
		if ("rating".equals(sort)) {
			users.sort(Comparator.comparing(User::getRating).reversed().thenComparing(User::getSurname));
		}
		request.setAttribute("userlist", users);
		request.setAttribute("sort", sort);
		return "/master_list.jsp";
	}
}
