package controller.command.impl;

import static service.utils.ValidatorUtil.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import controller.exceptions.FindingServiceException;
import controller.exceptions.IncorrectParamException;
import controller.exceptions.UpdatingUserException;
import entity.Role;
import entity.User;
import service.ServiceManager;
import service.UserManager;

/**
 * Updating user info
 * 
 * @author yevgenia.kovalova
 *
 */

public class UpdateUserCommand implements Command {
	private static final Logger logger = LogManager.getLogger(UpdateUserCommand.class);
	private UserManager userManager;
	public static final List<Role> ROLES_ALLOWED = new ArrayList<>(List.of(Role.ADMIN, Role.CLIENT, Role.HAIRDRESSER));
	public static final boolean IS_GUEST_ALLOWED = false;

	public UpdateUserCommand(UserManager userManager) {
		this.userManager = userManager;
	}

	public UpdateUserCommand() {
		this.userManager = UserManager.getInstance();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.trace("execute");

		User loggedUser = (User) request.getSession().getAttribute("user");
		if (!commandIsAllowed(loggedUser, ROLES_ALLOWED, IS_GUEST_ALLOWED)) {
			logger.info("Access denied.", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
			request.setAttribute("error", "Access denied");
			return "/error.jsp";
		}
		logger.trace("Access allowed", loggedUser, loggedUser == null ? "GUEST" : loggedUser.getRole());
		int id = 0;
		try {
			id = parseIntParameter(request.getParameter("id"));
		} catch (IncorrectParamException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String info = request.getParameter("info");
		logger.trace(isValidName(name));
		logger.trace(isValidName(surname));
		logger.trace(isValidEmail(email));
		if (!isValidName(name) || !isValidName(surname) || !isValidEmail(email)) {
			logger.error("Invalid parameters", name, surname, email);
			request.setAttribute("error", "Can't update user. Invalid input data.");
			return "/error.jsp";
		}
		if (loggedUser.getRole() != Role.ADMIN) {
			id = loggedUser.getId();
		}
		try {
			int i = 1;
			ServiceManager serviceManager = ServiceManager.getInstance();
			HashMap<Integer, Integer> serviceMap = new HashMap<>();
			while (request.getParameter("service" + i) != null) {
				String sumParam = request.getParameter("sum" + i);
				int sum = (sumParam == "") ? 0 : Integer.parseInt(request.getParameter("sum" + i));
				if (sum != 0) {
					serviceMap.put(serviceManager.findServiceByName(request.getParameter("service" + i)).getId(), sum);
				}
				i++;
			}
			User user = new User(id, email, "", name, surname, tel, null, info, false, 0, "");
			user = userManager.updateUser(user);
			if (!serviceMap.isEmpty()) {
				user = userManager.addServicesToUser(user, serviceMap);
			}
			if (user.getId() != 0) {
				if (loggedUser.getId() == user.getId()) {
					loggedUser.setEmail(user.getEmail());
					loggedUser.setName(user.getName());
					loggedUser.setSurname(user.getSurname());
					loggedUser.setTel(user.getTel());
					loggedUser.setInfo(user.getInfo());
					request.getSession().setAttribute("user", loggedUser);
				}
				if (loggedUser.getRole() != Role.ADMIN) {
					logger.info("user updated his info", id);
					request.setAttribute("redirect", "redirect");
					return "my_info.jsp";
				} else {
					logger.info("admin updated user info", id);
					request.setAttribute("message", "User data updated successfully!");
					request.setAttribute("redirect", "redirect");
					return "Controller?command=show_user_info&id=" + user.getId();
				}
			}
			logger.error("Can't update user");
			request.setAttribute("error", "Can't update user");
			return "/error.jsp";
		} catch (FindingServiceException | UpdatingUserException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}
}
