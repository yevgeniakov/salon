package command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import controller.command.impl.ChangeLocaleCommand;
import controller.command.impl.CreateAppointmentCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.AppointmentDao;
import dao.impl.UserDao;
import entity.Appointment;
import entity.Role;
import entity.Service;
import entity.User;
import service.AppointmentManager;
import service.UserManager;

public class CreateAppointmentCommandTest {
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
		User client = new User();
		client.setId(2);
		
		testAppointment.setUser(client);
		testAppointment.setDate(LocalDate.now());
		testAppointment.setTimeslot(11);
		when(dao.getPrice(any(Connection.class), any(User.class), any(Service.class))).thenReturn(200);
		when(dao.save(any(Connection.class), any(Appointment.class))).thenReturn(testAppointment);
		when(request.getParameter("service_id")).thenReturn("1");
		when(request.getParameter("master_id")).thenReturn("1");
		when(request.getParameter("user_id")).thenReturn("2");
		when(request.getParameter("timeslot")).thenReturn("11");
		when(request.getParameter("date")).thenReturn(LocalDate.now().toString());
		when(session.getAttribute("user")).thenReturn(null);
		when(request.getSession()).thenReturn(session);
		Command command = new CreateAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(session.getAttribute("user")).thenReturn(client);
		command = new CreateAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		client.setRole(Role.CLIENT);
		command = new CreateAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "Controller?command=show_master_schedule&id=" + testAppointment.getMaster().getId() + "&date="
				+ testAppointment.getDate());
		when(dao.save(any(Connection.class), any(Appointment.class))).thenThrow(SQLException.class);
		command = new CreateAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(request.getParameter("timeslot")).thenReturn("353");
		command = new CreateAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(request.getParameter("timeslot")).thenReturn("11");
		when(request.getParameter("date")).thenReturn(LocalDate.now().minusDays(4).toString());
		command = new CreateAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		
	}
}
