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

public class ShowUserListCommand implements Command {
	private static final Logger logger = LogManager.getLogger(ShowUserListCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute");
		
		User loggedUser = (User) request.getSession().getAttribute("user");

		if (loggedUser == null) {
			logger.info("unauthorized access. Redirecting to index page");
			return "/index.jsp";
		}

		String sort = request.getParameter("sort");
		String sortorder = request.getParameter("sortorder");
		Boolean isBlocked = (request.getParameter("isblocked") == null
				|| request.getParameter("isblocked").equals("null")) ? null
						: Boolean.parseBoolean(request.getParameter("isblocked"));
		String searchValue = request.getParameter("searchvalue");
		int itemsPerPage = (request.getParameter("itemsperpage") == null
				|| request.getParameter("itemsperpage").equals("null")) ? 10
						: Integer.parseInt(request.getParameter("itemsperpage"));
		int page = (request.getParameter("page") == null || request.getParameter("page").equals("null")) ? 1
				: Integer.parseInt(request.getParameter("page"));

		UserManager manager = UserManager.getInstance();
		List<User> users = new ArrayList<>();

		try {
			users = manager.findUsersByConditions(isBlocked, searchValue);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			request.setAttribute("error", "unable to get user list");
			return "/error.jsp";
		}

		if ("id".equals(sort)) {
			users.sort("desc".equals(sortorder) ? Comparator.comparing(User::getId).reversed()
					: Comparator.comparing(User::getId));
		}

		if ("surname".equals(sort)) {
			users.sort("desc".equals(sortorder) ? Comparator.comparing(User::getSurname).reversed()
					: Comparator.comparing(User::getSurname));
		}

		//// pagination

		int itemsAmount = users.size();
		int pagesTotal = itemsAmount / itemsPerPage;
		pagesTotal = (pagesTotal * itemsPerPage == itemsAmount ? pagesTotal : pagesTotal + 1);
		int indexTo = itemsPerPage * page;
		int indexFrom = indexTo - itemsPerPage;
		var subUsers = users.subList(indexFrom, Math.min(indexTo, itemsAmount));

		request.setAttribute("userlist", subUsers);
		request.setAttribute("page", page);
		request.setAttribute("pagesTotal", pagesTotal);
		request.setAttribute("itemsPerPage", itemsPerPage);
		request.setAttribute("sort", sort);
		request.setAttribute("sortOrder", sortorder);
		request.setAttribute("isBlocked", isBlocked);
		request.setAttribute("searchValue", searchValue);
		return "/user_list.jsp";
	}

}
