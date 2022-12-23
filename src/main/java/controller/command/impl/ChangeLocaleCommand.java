package controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.UpdatingUserException;
import entity.User;
import service.UserManager;

/**
 * Changes locale for current session and user
 * 
 * @author yevgenia.kovalova
 *
 */

public class ChangeLocaleCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CreateAppointmentCommand.class);
	private UserManager manager;

	public ChangeLocaleCommand(UserManager manager) {
		this.manager = manager;
	}

	public ChangeLocaleCommand() {
		this.manager = UserManager.getInstance();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");
		if (loggedUser != null) {
			String locale = request.getParameter("locale");
			logger.trace("locale is: " + locale);
			try {
				manager.setUserCurrentLang(loggedUser, locale);
			} catch (UpdatingUserException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return "/change_locale.jsp";
	}
}
