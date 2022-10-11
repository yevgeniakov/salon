package controller.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Role;
import entity.Service;
import entity.User;
import service.UserManager;

public class DeleteServiceFromMasterCommand implements Command {
	private static final Logger logger = LogManager.getLogger(DeleteServiceFromMasterCommand.class);
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
