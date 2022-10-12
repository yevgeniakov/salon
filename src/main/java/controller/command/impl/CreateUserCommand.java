package controller.command.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.CreatingUserException;
import controller.exceptions.FindingServiceException;
import controller.exceptions.IncorrectParamException;
import entity.Role;
import entity.User;
import service.ServiceManager;
import service.UserManager;
import static service.utils.ValidatorUtil.*;

public class CreateUserCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CreateUserCommand.class);
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(
	        List.of(Role.ADMIN));
	public static final boolean IS_GUEST_ALLOWED = true;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");
		
		User loggedUser = (User) request.getSession().getAttribute("user");
		if (!commandIsAllowed(loggedUser, ROLES_ALLOWED, IS_GUEST_ALLOWED)) {
			logger.info("Access denied.", loggedUser,
					loggedUser == null ? "GUEST" : loggedUser.getRole());
			
			request.setAttribute("error", "Access denied");
			return "/error.jsp";
		}

		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
		
		try {
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			Role role = Role.valueOf(request.getParameter("role").toUpperCase());
			String password = request.getParameter("password");
			String info = request.getParameter("info");
			
			if (!isValidName(name) 
					|| !isValidName(surname) 
					|| !isValidEmail(email) 
					|| !isValidPassword(password) 
					|| !isValidText(info)) {
				logger.error("Invalid parameters");
				request.setAttribute("error", "Can't create user. Invalid input data.");
				return "/error.jsp";
			}
			
			int i = 1;
			ServiceManager serviceManager = ServiceManager.getInstance();
			HashMap<Integer, Integer> serviceMap = new HashMap<>();
			while (request.getParameter("service" + i) != null) {
				String sumParam = request.getParameter("sum" + i);
				int sum = (sumParam == "") ? 0 : parseIntParameter(request.getParameter("sum" + i));
				if (sum != 0) {
					serviceMap.put(serviceManager.findServiceByName(request.getParameter("service" + i)).getId(), sum);
				}
				i++;
			}

			User user = new User(0, email, password, name, surname, tel, role, info, false, 0, "en");
			UserManager userManager = UserManager.getInstance();
			if (serviceMap.isEmpty()) {
				user = userManager.createUser(user);
			} else {
				user = userManager.createUserWithServices(user, serviceMap);
			}

			if (user.getId() != 0) {
				
				if (loggedUser == null) {
					logger.info("new registration", user.getId(), name, surname);
					request.getSession().setAttribute("user", user);
					return "client_page.jsp";
				}
				if (loggedUser.getRole() == Role.ADMIN) {
					logger.info("new user created by Admin", user.getId(), name, surname);
					return "Controller?command=show_user_info&id=" + user.getId();
				}

				return "index.jsp";
			}
			
			logger.error("Can't create user");
			request.setAttribute("error", "Can't create user");
			return "/error.jsp";
		}  catch (CreatingUserException | FindingServiceException | IncorrectParamException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
