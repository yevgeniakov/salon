package controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.CheckCredentialsException;
import controller.exceptions.FindingUserException;
import entity.User;
import service.UserManager;

public class LoginCommand implements Command {
	private static final Logger logger = LogManager.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserManager manager = UserManager.getInstance();
		User user = null;
		try {
			user = manager.checkCredentials(email, password);
		} catch (CheckCredentialsException | FindingUserException e) {
			logger.info("Check credentials failed. " + e.getMessage());
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
		if (request.getSession(true).getAttribute("user") == null) {
			request.getSession().setAttribute("user", user);
			logger.info("user logged in", user.getId(), user.getName(), user.getSurname());
			request.setAttribute("redirect", "redirect");
			logger.info("change_locale.jsp?locale=" + user.getCurrentLang());
			return "change_locale.jsp?locale=" + user.getCurrentLang();
		}
		logger.error("user is logged already. ");
		request.setAttribute("error", "you are logged!");
		return "/error.jsp";
	}

}
