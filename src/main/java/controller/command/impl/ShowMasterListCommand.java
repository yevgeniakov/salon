package controller.command.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.command.Command;
import entity.User;
import service.UserManager;

public class ShowMasterListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowMasterListCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");

		String sort = request.getParameter("sort");

		// TODO Validator
		UserManager manager = UserManager.getInstance();
		List<User> users = new ArrayList<>();
		try {
			users = manager.findAllMasters();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("error", "unable to get master list");
			return "/error.jsp";
		}

		if (sort == null || "".equals(sort) || "surname".equals(sort)) {
			users.sort(Comparator.comparing(User::getSurname));
		}

		if ("rating".equals(sort)) {
			users.sort(Comparator.comparing(User::getRating).reversed().thenComparing(User::getSurname));
		}

		request.setAttribute("userlist", users);
		request.setAttribute("sort", sort);
		return "/master_list.jsp";
	}

}
