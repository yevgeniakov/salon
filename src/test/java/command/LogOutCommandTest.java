package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import controller.command.Command;
import controller.command.impl.LogOutCommand;
import controller.exceptions.CreatingUserException;
import entity.User;

public class LogOutCommandTest {

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;

	@Before
	public void setUp() {
		this.request = mock(HttpServletRequest.class);
		this.response = mock(HttpServletResponse.class);
		this.session = mock(HttpSession.class);
	}

	@Test
	public void testLogOutCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		User testUser = new User();
		testUser.setId(7);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(null);
		Command command = new LogOutCommand();
		assertEquals(command.execute(request, response), "/index.jsp");
		when(session.getAttribute("user")).thenReturn(testUser);
		assertEquals(command.execute(request, response), "index.jsp");
	}
}
