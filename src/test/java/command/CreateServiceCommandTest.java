package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import controller.command.impl.CreateServiceCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.ServiceDao;
import entity.Role;
import entity.Service;
import entity.User;
import service.ServiceManager;

public class CreateServiceCommandTest {
	@Mock
	private ServiceDao dao;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	@InjectMocks
	private ServiceManager serviceManager;

	@Before
	public void setUp() {
		this.dao = mock(ServiceDao.class);
		this.request = mock(HttpServletRequest.class);
		this.response = mock(HttpServletResponse.class);
		this.session = mock(HttpSession.class);
		this.serviceManager = new ServiceManager(dao);
	}

	@Test
	public void testCreateServiceCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		Service testService = new Service();
		testService.setName("Haircut");
		testService.setInfo("bnslbnsnb");
		testService.setId(0);
		User loggedUser = new User();
		loggedUser.setId(1);
		when(dao.save(any(Connection.class), any(Service.class))).thenReturn(testService);
		when(request.getParameter("name")).thenReturn("hgh");
		when(request.getParameter("info")).thenReturn("dfhd");
		when(session.getAttribute("user")).thenReturn(null);
		when(request.getSession()).thenReturn(session);
		Command command = new CreateServiceCommand(serviceManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(session.getAttribute("user")).thenReturn(loggedUser);
		assertEquals(command.execute(request, response), "/error.jsp");
		loggedUser.setRole(Role.ADMIN);
		assertEquals(command.execute(request, response), "/error.jsp");
		testService.setId(5);
		assertEquals(command.execute(request, response), "Controller?command=show_service_list");
		when(dao.save(any(Connection.class), any(Service.class))).thenThrow(SQLException.class);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(request.getParameter("name")).thenReturn(null);
		assertEquals(command.execute(request, response), "/error.jsp");
	}
}
