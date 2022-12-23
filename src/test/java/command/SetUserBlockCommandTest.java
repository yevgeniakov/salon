package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import controller.command.Command;
import controller.command.impl.SetUserBlockCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.UserDao;
import entity.Role;
import entity.User;
import service.UserManager;

public class SetUserBlockCommandTest {
	@Mock
	private UserDao dao;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	@InjectMocks
	private UserManager userManager;

	@Before
	public void setUp() {
		this.dao = mock(UserDao.class);
		this.request = mock(HttpServletRequest.class);
		this.response = mock(HttpServletResponse.class);
		this.session = mock(HttpSession.class);
		this.userManager = new UserManager(dao);
	}

	@Test
	public void testSetUserBlockCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		testUser.setName("Ivan");
		testUser.setSurname("Petrov");
		testUser.setId(7);
		User loggedUser = new User();
		loggedUser.setId(1);
		loggedUser.setRole(Role.CLIENT);
		when(dao.findById(any(Connection.class), anyInt())).thenReturn(testUser);
		when(dao.setUserBlock(any(Connection.class), any(User.class), anyBoolean())).thenReturn(testUser);
		when(request.getParameter("id")).thenReturn("5");
		when(request.getParameter("isBlocked")).thenReturn("true");
		when(request.getSession()).thenReturn(session);
		Command command = new SetUserBlockCommand(userManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(session.getAttribute("user")).thenReturn(loggedUser);
		assertEquals(command.execute(request, response), "/error.jsp");
		loggedUser.setRole(Role.ADMIN);
		assertEquals(command.execute(request, response), "Controller?command=show_user_info&id=" + testUser.getId());
		when(dao.setUserBlock(any(Connection.class), any(User.class), anyBoolean())).thenThrow(SQLException.class);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(request.getParameter("email")).thenReturn(null);
		assertEquals(command.execute(request, response), "/error.jsp");
	}
}
