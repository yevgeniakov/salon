package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
import controller.command.impl.DeleteServiceFromMasterCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.UserDao;
import entity.Role;
import entity.Service;
import entity.User;
import service.UserManager;

public class DeleteServiceFromMasterCommandTest {
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
	public void testDeleteServiceFromMasterCommand()
			throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		testUser.setName("Ivan");
		testUser.setSurname("Petrov");
		testUser.setId(7);
		User loggedUser = new User();
		loggedUser.setId(1);
		Service testService = new Service();
		testService.setId(2);
		testService.setName("Manicure");
		when(request.getParameter("master_id")).thenReturn("7");
		when(request.getParameter("service_id")).thenReturn("2");
		when(request.getSession()).thenReturn(session);
		Command command = new DeleteServiceFromMasterCommand(userManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(session.getAttribute("user")).thenReturn(loggedUser);
		assertEquals(command.execute(request, response), "/error.jsp");
		loggedUser.setRole(Role.ADMIN);
		assertEquals(command.execute(request, response), "Controller?command=show_user_info&id=" + testUser.getId());
		doThrow(SQLException.class).when(dao).deleteServiceFromMaster(any(Connection.class), any(User.class),
				any(Service.class));
		assertEquals(command.execute(request, response), "/error.jsp");
		when(request.getParameter("master_id")).thenReturn(null);
		assertEquals(command.execute(request, response), "/error.jsp");
	}
}
