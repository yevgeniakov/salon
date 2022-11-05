package manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import controller.exceptions.CreatingAppointmentException;
import controller.exceptions.DeletingAppointmentException;
import controller.exceptions.FindingAppointmentException;
import controller.exceptions.UpdatingAppointmentException;
import dao.impl.AppointmentDao;
import entity.Role;
import entity.Service;
import entity.User;
import entity.Appointment;
import service.AppointmentManager;

public class AppointmentManagerTest {
	@Mock
	private AppointmentDao dao;
	@InjectMocks
	private AppointmentManager appointmentManager;

	@Before
	public void setUp() {
		this.dao = mock(AppointmentDao.class);
		this.appointmentManager = new AppointmentManager(dao);
	}

	@Test
	public void testAddAppointment() throws ClassNotFoundException, CreatingAppointmentException, SQLException {
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
		when(dao.save(any(Connection.class), any(Appointment.class))).thenReturn(testAppointment);
		Appointment appointment = new Appointment();
		
		assertEquals(appointmentManager.createAppointment(appointment).getMaster(), master);
		
		when(dao.save(any(Connection.class), any(Appointment.class))).thenThrow(SQLException.class);
		try {
			assertEquals(appointmentManager.createAppointment(appointment).getMaster(), master);
			fail();
		} catch (CreatingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(appointmentManager.createAppointment(appointment).getMaster(), master);
			fail();
		} catch (CreatingAppointmentException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testSetAppointmentPay() throws ClassNotFoundException, UpdatingAppointmentException, SQLException {
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
		appointmentManager.setPayAppointment(testAppointment, false);
		verify(dao, times(1)).setIsPaid(dao.getConnection(),testAppointment, false);
	
		doThrow(SQLException.class).when(dao).setIsPaid(isA(Connection.class), isA(Appointment.class), isA(Boolean.class));
		try {
			appointmentManager.setPayAppointment(testAppointment, false);
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			appointmentManager.setPayAppointment(testAppointment, false);
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testSetAppointmentDone() throws ClassNotFoundException, UpdatingAppointmentException, SQLException {
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
		appointmentManager.setDoneAppointment(testAppointment, false);
		verify(dao, times(1)).setIsDone(dao.getConnection(),testAppointment, false);
	
		doThrow(SQLException.class).when(dao).setIsDone(isA(Connection.class), isA(Appointment.class), isA(Boolean.class));
		try {
			appointmentManager.setDoneAppointment(testAppointment, false);
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			appointmentManager.setDoneAppointment(testAppointment, false);
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testSetAppointmentFeedback() throws ClassNotFoundException, UpdatingAppointmentException, SQLException {
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
		
		appointmentManager.setFeedbackForAppointmentandUpdateMaster(testAppointment, 2.78, "hhh");
		verify(dao, times(1)).setFeedback(dao.getConnection(), testAppointment, "hhh", 2.78);
	
		doThrow(SQLException.class).when(dao).setFeedback(isA(Connection.class), isA(Appointment.class), isA(String.class), isA(Double.class));
		try {
			appointmentManager.setFeedbackForAppointmentandUpdateMaster(testAppointment, 2.78, "hhh");
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			appointmentManager.setFeedbackForAppointmentandUpdateMaster(testAppointment, 2.78, "hhh");
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testSetAppointmentTime() throws ClassNotFoundException, UpdatingAppointmentException, SQLException {
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
		appointmentManager.setTimeAppointment(testAppointment, LocalDate.now(), 12);
		verify(dao, times(1)).setTime(dao.getConnection(), testAppointment, LocalDate.now(), 12);
	
		doThrow(SQLException.class).when(dao).setTime(isA(Connection.class), isA(Appointment.class), isA(LocalDate.class), isA(Integer.class));
		try {
			appointmentManager.setTimeAppointment(testAppointment, LocalDate.now(), 12);
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			appointmentManager.setTimeAppointment(testAppointment, LocalDate.now(), 12);
			fail();
		} catch (UpdatingAppointmentException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testFindAppointmentByKey() throws ClassNotFoundException, FindingAppointmentException, SQLException {
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
		when(dao.findByKey(any(Connection.class), anyInt(), any(LocalDate.class), anyInt())).thenReturn(testAppointment);
		assertEquals(appointmentManager.findAppointmentByKey(1, LocalDate.now(), 11), testAppointment);
		when(dao.findByKey(any(Connection.class), anyInt(), any(LocalDate.class), anyInt())).thenThrow(SQLException.class);
		try {
			assertEquals(appointmentManager.findAppointmentByKey(1, LocalDate.now(), 11), testAppointment);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(appointmentManager.findAppointmentByKey(1, LocalDate.now(), 11), testAppointment);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindAllAppointments() throws ClassNotFoundException, FindingAppointmentException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		List<Appointment> appointments = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Appointment testAppointment = new Appointment();
			appointments.add(testAppointment);
		}
		when(dao.findAll(any(Connection.class))).thenReturn(appointments);
		assertEquals(appointmentManager.findAllAppointments().size(), 4);
		when(dao.findAll(any(Connection.class))).thenThrow(SQLException.class);
		try {
			assertEquals(appointmentManager.findAllAppointments().size(), 4);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(appointmentManager.findAllAppointments().size(), 4);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	
	@Test
	public void testFindAppointmentsByConditions() throws ClassNotFoundException, FindingAppointmentException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		List<Appointment> appointments = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Appointment testAppointment = new Appointment();
			appointments.add(testAppointment);
		}
		when(dao.findByConditions(any(Connection.class), any(LocalDate.class), any(LocalDate.class), anyInt(), anyInt(), anyInt(), anyBoolean(), anyBoolean(),  anyBoolean())).thenReturn(appointments);
		assertEquals(appointmentManager.findAppointmentsByConditions(LocalDate.now(), LocalDate.now(), 3, 6, 7, true, true, true), appointments);
		when(dao.findByConditions(any(Connection.class), any(LocalDate.class), any(LocalDate.class), anyInt(), anyInt(), anyInt(), anyBoolean(), anyBoolean(),  anyBoolean())).thenThrow(SQLException.class);
		try {
			assertEquals(appointmentManager.findAppointmentsByConditions(LocalDate.now(), LocalDate.now(), 3, 6, 7, true, true, true), appointments);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(appointmentManager.findAppointmentsByConditions(LocalDate.now(), LocalDate.now(), 3, 6, 7, true, true, true), appointments);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testGetPriceByMasterAndService() throws ClassNotFoundException, FindingAppointmentException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User master = new User();
		master.setId(1);
		Service service = new Service(1, "dfjkddngd", "hgbnfnkgfjjfj");
		when(dao.getPrice(any(Connection.class), any(User.class), any(Service.class))).thenReturn(500);
		assertEquals(appointmentManager.getPriceByMasterAndService(master, service), 500);
		when(dao.getPrice(any(Connection.class), any(User.class), any(Service.class))).thenThrow(SQLException.class);
		try {
			assertEquals(appointmentManager.getPriceByMasterAndService(master, service), 500);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(appointmentManager.getPriceByMasterAndService(master, service), 500);
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testGetMaserFreeSlots() throws ClassNotFoundException, FindingAppointmentException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User master = new User();
		master.setId(1);
		when(dao.getMasterFreeSlots(any(Connection.class), any(LocalDate.class), any(User.class))).thenReturn(List.of(13, 14, 17, 18));
		assertEquals(appointmentManager.getMasterFreeSlots(LocalDate.now(), master).size(), 4);
		when(dao.getMasterFreeSlots(any(Connection.class), any(LocalDate.class), any(User.class))).thenThrow(SQLException.class);
		try {
			assertEquals(appointmentManager.getMasterFreeSlots(LocalDate.now(), master).size(), 4);			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(appointmentManager.getMasterFreeSlots(LocalDate.now(), master).size(), 4);			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testGetMaserShedule() throws ClassNotFoundException, FindingAppointmentException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User master = new User();
		master.setId(1);
		List<Appointment> appointments = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Appointment testAppointment = new Appointment();
			appointments.add(testAppointment);
		}
		when(dao.getMasterSchedule(any(Connection.class), any(LocalDate.class), any(User.class))).thenReturn(appointments);
		assertEquals(appointmentManager.getMasterSchedule(LocalDate.now(), master).size(), 4);
		when(dao.getMasterSchedule(any(Connection.class), any(LocalDate.class), any(User.class))).thenThrow(SQLException.class);
		try {
			assertEquals(appointmentManager.getMasterSchedule(LocalDate.now(), master).size(), 4);			
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(appointmentManager.getMasterSchedule(LocalDate.now(), master).size(), 4);			
			fail();
		} catch (FindingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testDeleteAppointment() throws ClassNotFoundException, SQLException, DeletingAppointmentException {
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
		appointmentManager.deleteAppointment(testAppointment);
		verify(dao, times(1)).delete(dao.getConnection(), testAppointment);
	
		doThrow(SQLException.class).when(dao).delete(isA(Connection.class), isA(Appointment.class));
		try {
			appointmentManager.deleteAppointment(testAppointment);
			fail();
		} catch (DeletingAppointmentException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			appointmentManager.deleteAppointment(testAppointment);
			fail();
		} catch (DeletingAppointmentException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testGetInstanse() {
		AppointmentManager manager1 = AppointmentManager.getInstance();
		AppointmentManager manager2 = AppointmentManager.getInstance();
		
		assertEquals(manager1, manager2);
	}
}
