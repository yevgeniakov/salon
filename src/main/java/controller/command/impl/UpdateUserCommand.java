package controller.command.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.Role;
import entity.User;
import service.ServiceManager;
import service.UserManager;

public class UpdateUserCommand implements Command {
	private static final Logger logger = LogManager.getLogger(UpdateUserCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String info = request.getParameter("info");
			boolean isBlocked = Boolean.parseBoolean(request.getParameter("isBlocked"));

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
			User user = new User(id, email, "", name, surname, tel, null, info, isBlocked, 0);

			UserManager userManager = UserManager.getInstance();
			user = userManager.updateUser(user);

			if (!serviceMap.isEmpty()) {
				user = userManager.addServicesToUser(user, serviceMap);
			}

			if (user.getId() != 0) {
				User loggedUser = (User) request.getSession().getAttribute("user");
				if (loggedUser != null && loggedUser.getId() == user.getId()) {
					loggedUser.setEmail(user.getEmail());
					loggedUser.setName(user.getName());
					loggedUser.setSurname(user.getSurname());
					loggedUser.setTel(user.getTel());
					loggedUser.setInfo(user.getInfo());
					request.getSession().setAttribute("user", loggedUser);
				}

				if (loggedUser == null) {

					return "index.jsp";
				}
				if (loggedUser.getRole() != Role.ADMIN) {
					return "my_info.jsp";
				} else {
					return "Controller?command=show_user_info&id=" + user.getId();
				}
			}
			request.setAttribute("error", "Can't update user");
			return "/error.jsp";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			request.setAttribute("error", e.getMessage());
			return "/error.jsp";
		}
	}

}
