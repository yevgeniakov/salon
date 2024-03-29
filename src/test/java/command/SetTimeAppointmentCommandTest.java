package command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import controller.command.impl.SetTimeAppointmentCommand;
import controller.exceptions.CreatingUserException;
import dao.impl.AppointmentDao;
import entity.Appointment;
import entity.Role;
import entity.User;
import service.AppointmentManager;

public class SetTimeAppointmentCommandTest {
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
	public void testSetTimeAppointmentCommand() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		Appointment testAppointment = new Appointment();
		User master = new User();
		master.setId(1);
		master.setRole(Role.HAIRDRESSER);
		testAppointment.setMaster(master);
		User client = new User();
		client.setId(2);
		client.setRole(Role.CLIENT);
		User admin = new User();
		admin.setId(33);
		admin.setRole(Role.ADMIN);
		testAppointment.setUser(client);
		testAppointment.setDate(LocalDate.now());
		testAppointment.setTimeslot(11);
		when(dao.findByKey(any(Connection.class), anyInt(), any(LocalDate.class), anyInt()))
				.thenReturn(testAppointment);
		when(request.getParameter("master_id")).thenReturn("1");
		when(request.getParameter("timeslot")).thenReturn("11");
		when(request.getParameter("newtimeslot")).thenReturn("12");
		when(request.getParameter("date")).thenReturn(LocalDate.now().toString());
		when(request.getParameter("newdate")).thenReturn(LocalDate.now().plusDays(1).toString());
		when(session.getAttribute("user")).thenReturn(null);
		when(request.getSession()).thenReturn(session);
		Command command = new SetTimeAppointmentCommand(appointmentManager);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(session.getAttribute("user")).thenReturn(client);
		assertEquals(command.execute(request, response), "/error.jsp");
		when(session.getAttribute("user")).thenReturn(admin);
		assertEquals(command.execute(request, response), "Controller?command=show_master_schedule&id="
				+ testAppointment.getMaster().getId() + "&date=" + testAppointment.getDate());
		doThrow(SQLException.class).when(dao).setTime(any(Connection.class), any(Appointment.class),
				any(LocalDate.class), anyInt());
		assertEquals(command.execute(request, response), "/error.jsp");
		when(request.getParameter("timeslot")).thenReturn("sd4");
		assertEquals(command.execute(request, response), "/error.jsp");

	}
}
