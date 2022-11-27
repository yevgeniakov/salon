package controller.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.IncorrectParamException;
import controller.exceptions.UpdatingUserException;
import entity.Role;
import entity.User;
import service.UserManager;
import service.utils.ValidatorUtil;

/**
 * Marks user as blocked/unblocked (Admin only)
 * 
 * @author yevgenia.kovalova
 *
 */

public class SetUserBlockCommand implements Command {
	private static final Logger logger = LogManager.getLogger(SetUserBlockCommand.class);
	private UserManager manager;
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
		User user = new User();
			int id;
			boolean isBlocked;
			try {
				id = ValidatorUtil.parseIntParameter(request.getParameter("id"));
				isBlocked = ValidatorUtil.parseBooleanParameter(request.getParameter("isBlocked"));
			} catch (IncorrectParamException e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("error", e.getMessage());
				return "/error.jsp";
			}
			try {
			user = manager.setUserBlock(id, isBlocked);
			if (user.getId() != 0) {
				logger.info("user set blocked = " + isBlocked, id);
				request.setAttribute("redirect", "redirect");
				return "Controller?command=show_user_info&id=" + user.getId();
			}
			logger.error("Can't set user block");
			request.setAttribute("error", "Can't set user block");
			return "/error.jsp";
		} catch (UpdatingUserException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
 	public SetUserBlockCommand(UserManager manager) {
		this.manager = manager;
	}
 	public SetUserBlockCommand() {
		this.manager = UserManager.getInstance();
	}
}
