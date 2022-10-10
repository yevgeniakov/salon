package controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Role;
import entity.Service;
import entity.User;
import service.ServiceManager;

public class CreateServiceCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CreateServiceCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");
		
		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}
		
		try {
			String name = request.getParameter("name");
			String info = request.getParameter("info");
			Service service = new Service(0, name, info);
			ServiceManager manager = ServiceManager.getInstance();
			service = manager.createService(service);

			if (service.getId() != 0) {
				
				if (loggedUser.getRole() == Role.ADMIN) {
					return "Controller?command=show_service_list";
				}
				return "index.jsp";
			}
			
			logger.error("Service is not created");
			request.setAttribute("error", "Can't create service");
			return "/error.jsp";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "Can't create service");
			return "/error.jsp";
		}
	}

}
