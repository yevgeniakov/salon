package controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.User;

/**
 * Makes log-out for logged user
 * 
 * @author yevgenia.kovalova
 *
 */
public class LogOutCommand implements Command {
	private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");
		if (loggedUser == null) {
			logger.info("user is not logged in, but trying to log out");
			return "/index.jsp";
		}
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		logger.info("user logged out successfully", loggedUser.getId());
		request.setAttribute("redirect", "redirect");
		return "index.jsp";
	}
}
