package controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Service;
import entity.User;
import service.UserManager;

public class DeleteServiceFromMasterCommand implements Command {
	private static final Logger logger = LogManager.getLogger(DeleteServiceFromMasterCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");
		
		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}

		try {
			int master_id = Integer.parseInt(request.getParameter("master_id"));
			int service_id = Integer.parseInt(request.getParameter("service_id"));

			User master = new User();
			master.setId(master_id);

			Service service = new Service();
			service.setId(service_id);

			UserManager manager = UserManager.getInstance();
			manager.deleteServiceFromMaster(master, service);

			request.setAttribute("message", "appointment deleted");
			return "Controller?command=show_user_info&id=" + master_id;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
