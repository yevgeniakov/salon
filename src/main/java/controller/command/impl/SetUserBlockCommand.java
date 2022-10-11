package controller.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Role;
import entity.User;
import service.UserManager;

public class SetUserBlockCommand implements Command {
	private static final Logger logger = LogManager.getLogger(SetUserBlockCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN));
	public static final boolean IS_GUEST_ALLOWED = false;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");
		
		User loggedUser = (User) request.getSession().getAttribute("user");
		if (!commandIsAllowed(loggedUser, ROLES_ALLOWED, IS_GUEST_ALLOWED)) {
			logger.info("Access denied. returning to index page", loggedUser,
					loggedUser == null ? "GUEST" : loggedUser.getRole());
			
			return "/index.jsp";
		}

		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());

		User user = new User();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			boolean isBlocked = Boolean.parseBoolean(request.getParameter("isBlocked"));

			UserManager manager = UserManager.getInstance();
			user = manager.setUserBlock(id, isBlocked);

			if (user.getId() != 0) {
				logger.info("user set blocked = " + isBlocked, id);
				return "Controller?command=show_user_info&id=" + user.getId();
			}
			logger.error("Can't set user block");
			request.setAttribute("error", "Can't set user block");
			return "/error.jsp";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
}
