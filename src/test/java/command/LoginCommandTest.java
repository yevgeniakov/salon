package command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
import controller.command.impl.LoginCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.UserDao;
import entity.User;
import service.UserManager;
import service.utils.PasswordEncodingService;

public class LoginCommandTest {
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
	public void testLoginCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		
		testUser.setName("Ivan");
		testUser.setSurname("Petrov");
		testUser.setId(7);
		testUser.setCurrentLang("uk");
		try {
			testUser.setPassword(PasswordEncodingService.generateStorngPasswordHash("12345"));
		} catch (NoSuchAlgorithmException e) {
			fail();
		} catch (InvalidKeySpecException e) {
			fail();
		}
		when(dao.findByEmail(any(Connection.class), anyString())).thenReturn(testUser);
		
		when(request.getParameter("email")).thenReturn("aaa@aa.aa");
		when(request.getParameter("password")).thenReturn("12345");
		
		when(request.getSession()).thenReturn(session);
		when(request.getSession(true)).thenReturn(session);
		when(dao.findByEmail(any(Connection.class), anyString())).thenReturn(testUser);
		Command command = new LoginCommand(userManager);
		assertEquals(command.execute(request, response), "change_locale.jsp?locale=" + testUser.getCurrentLang());
		when(dao.findByEmail(any(Connection.class), anyString())).thenThrow(SQLException.class);
		command = new LoginCommand(userManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(request.getParameter("password")).thenReturn("125");
		command = new LoginCommand(userManager);
		assertEquals(command.execute(request, response), "/error.jsp");
	}
}
