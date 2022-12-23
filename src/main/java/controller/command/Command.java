package controller.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Role;
import entity.User;

public interface Command {

	default boolean commandIsAllowed(User loggedUser, List<Role> rolesAllowed, boolean isGuestAllowed) {
		if (loggedUser == null) {
			return isGuestAllowed;
		}
		return rolesAllowed.contains(loggedUser.getRole());
	}

	String execute(HttpServletRequest request, HttpServletResponse response);
}