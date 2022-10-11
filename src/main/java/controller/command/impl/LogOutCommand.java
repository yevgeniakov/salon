package controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;

public class LogOutCommand implements Command {
	private static final Logger logger = LogManager.getLogger(LogOutCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");
		
		try {
			request.getSession().removeAttribute("user");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "failed to logout");
			return "/error.jsp";
		}
		logger.info("user logged out successfully");
		return "/index.jsp";
	}

}
