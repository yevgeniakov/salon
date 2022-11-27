package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import controller.command.Command;
import controller.command.impl.CreateUserCommand;
import controller.command.impl.ShowMasterListCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.UserDao;
import entity.Role;
import entity.User;
import service.UserManager;

public class ShowMastersListCommandTest {
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
	public void testShowMastersListCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser1 = new User();
		testUser1.setName("Ivan");
		testUser1.setSurname("Petrov");
		testUser1.setId(7);
		User testUser2 = new User();
		testUser2.setName("Petro");
		testUser2.setSurname("Sidorov");
		testUser2.setId(17);
		ArrayList<User> testMasters = new ArrayList<>();
		testMasters.add(testUser1);
		testMasters.add(testUser2);
		User loggedUser = new User();
		loggedUser.setId(1);
		loggedUser.setRole(Role.HAIRDRESSER);
		when(dao.findAllMasters(any(Connection.class))).thenReturn(testMasters);
		when(request.getSession()).thenReturn(session);
		Command command = new ShowMasterListCommand(userManager);
		assertEquals(command.execute(request, response), "/master_list.jsp");
		when(session.getAttribute("user")).thenReturn(loggedUser);
		assertEquals(command.execute(request, response), "/error.jsp");
		loggedUser.setRole(Role.ADMIN);
		assertEquals(command.execute(request, response), "/master_list.jsp");
		when(dao.findAllMasters(any(Connection.class))).thenThrow(SQLException.class);
		assertEquals(command.execute(request, response), "/error.jsp");
	}
}
