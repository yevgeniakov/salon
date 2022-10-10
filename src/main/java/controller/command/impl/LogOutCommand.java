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
		logger.info("execute");
		
		try {
			request.getSession().removeAttribute("user");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		logger.info("user logged out successfully");
		return "/index.jsp";
	}

}
