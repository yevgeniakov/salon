package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import controller.command.impl.ChangeLocaleCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.UserDao;
import entity.User;
import service.UserManager;

public class ChangeLocaleCommandTest {
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
	public void testChangeLocaleCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		testUser.setId(1);
		when(dao.findById(any(Connection.class), anyInt())).thenReturn(testUser);
		when(dao.setUserCurrentLang(any(Connection.class), any(User.class), anyString())).thenReturn(testUser);
		when(request.getParameter("locale")).thenReturn("uk");
		when(session.getAttribute("user")).thenReturn(testUser);
		when(request.getSession()).thenReturn(session);
		Command command = new ChangeLocaleCommand(userManager);
		assertEquals(command.execute(request, response), "/change_locale.jsp");
		when(dao.setUserCurrentLang(any(Connection.class), any(User.class), anyString())).thenThrow(SQLException.class);
		command = new ChangeLocaleCommand(userManager);
		assertEquals(command.execute(request, response), "/change_locale.jsp");
		when(session.getAttribute("user")).thenReturn(null);
		command = new ChangeLocaleCommand(userManager);
		assertEquals(command.execute(request, response), "/change_locale.jsp");
	}
}
