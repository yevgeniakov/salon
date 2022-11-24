package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import controller.command.Command;
import controller.command.impl.CreateAppointmentCommand;
import controller.command.impl.DeleteAppointmentCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.AppointmentDao;
import entity.Appointment;
import entity.Role;
import entity.Service;
import entity.User;
import service.AppointmentManager;

public class DeleteAppointmentCommandTest {
	@Mock
	private AppointmentDao dao;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	@InjectMocks
	private AppointmentManager appointmentManager;

	@Before
	public void setUp() {
		this.dao = mock(AppointmentDao.class);
		this.request = mock(HttpServletRequest.class); 
		this.response = mock(HttpServletResponse.class);
		this.session = mock(HttpSession.class);
		this.appointmentManager = new AppointmentManager(dao);
	}
	
	@Test
	public void testCreateAppointmentCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		Appointment testAppointment = new Appointment();
		User master = new User();
		master.setId(1);
		testAppointment.setMaster(master);
		User loggedUser = new User();
		loggedUser.setId(2);
		
		testAppointment.setUser(loggedUser);
		testAppointment.setDate(LocalDate.now());
		testAppointment.setTimeslot(11);
		when(request.getParameter("master_id")).thenReturn("1");
		when(request.getParameter("timeslot")).thenReturn("11");
		when(request.getParameter("date")).thenReturn(LocalDate.now().toString());
		when(session.getAttribute("user")).thenReturn(null);
		when(request.getSession()).thenReturn(session);
		Command command = new DeleteAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(session.getAttribute("user")).thenReturn(loggedUser);
		command = new DeleteAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		loggedUser.setRole(Role.ADMIN);
		command = new DeleteAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "Controller?command=show_master_schedule&id=" + "1" + "&date=" + LocalDate.now().toString());
		when(request.getParameter("timeslot")).thenReturn("353");
		command = new DeleteAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		doThrow(SQLException.class).when(dao).delete(isA(Connection.class), isA(Appointment.class));
		command = new DeleteAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
	}
}
