package controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.User;
import service.UserManager;

public class SetUserBlockCommand implements Command {
	private static final Logger logger = LogManager.getLogger(SetUserBlockCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}
		User user = new User();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			boolean isBlocked = Boolean.parseBoolean(request.getParameter("isBlocked"));

			UserManager manager = UserManager.getInstance();
			user = manager.setUserBlock(id, isBlocked);

			if (user.getId() != 0) {
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
