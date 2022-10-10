package controller.command.impl;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Role;
import entity.Service;
import entity.User;
import service.ServiceManager;
import service.UserManager;

public class ShowUserInfoCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowUserInfoCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		int id = 0;

		if (!(request.getParameter("id") == null)) {
			id = Integer.parseInt(request.getParameter("id"));
		} else {
			HttpSession session = request.getSession(false);
			User loggedUser = null;
			if (session != null) {
				loggedUser = (User) session.getAttribute("user");
			}
			if (!(loggedUser == null)) {

				id = loggedUser.getId();
			}
		}

		if (id == 0) {
			logger.error("unable to find user");
			request.setAttribute("error", "unable to find user!");
			return "/error.jsp";
		}

		UserManager userManager = UserManager.getInstance();
		User user = new User();
		try {
			user = userManager.findUserbyID(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to find user!");
			return "/error.jsp";
		}

		ServiceManager serviceManager = ServiceManager.getInstance();
		TreeMap<Service, Integer> services = new TreeMap<>();
		if (user.getRole() == Role.HAIRDRESSER) {
			services = new TreeMap<>();
			try {
				logger.debug("start to find all services for master");
				services = serviceManager.findAllServicesByMaster(user.getId());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				request.setAttribute("error", e.getMessage());
				return "/error.jsp";
			}
		}

		request.setAttribute("services", services);
		request.setAttribute("showuser", user);
		return "/user_info.jsp";
	}

}
