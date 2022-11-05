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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import controller.exceptions.CreatingUserException;
import controller.exceptions.FindingUserException;
import controller.exceptions.UpdatingUserException;
import dao.impl.UserDao;
import entity.Role;
import entity.Service;
import entity.User;
import service.UserManager;

public class UserManagerTest {
	@Mock
	private UserDao dao;
	@InjectMocks
	private UserManager userManager;

	@Before
	public void setUp() {
		this.dao = mock(UserDao.class);
		this.userManager = new UserManager(dao);
	}

	@Test
	public void testAddUser() throws ClassNotFoundException, CreatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		testUser.setId(1);
		when(dao.save(any(Connection.class), any(User.class))).thenReturn(testUser);
		User user = new User();
		user.setId(1);
		user.setName("Ivan");
		user.setSurname("Petrov");
		user.setEmail("ivan.petrov@bestemail.com");
		user.setPassword("12345");
		assertEquals(userManager.createUser(user).getId(), 1);
		when(dao.findByEmail(any(Connection.class), anyString())).thenReturn(testUser);
		try {
			assertEquals(userManager.createUser(user).getId(), 1);
			fail();
		} catch (CreatingUserException e) {
			assertNotNull(e);
		}
		when(dao.save(any(Connection.class), any(User.class))).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.createUser(user).getId(), 1);
			fail();
		} catch (CreatingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.createUser(user).getId(), 1);
			fail();
		} catch (CreatingUserException e) {
			assertNotNull(e);
		}
		
		
		
	}

	@Test
	public void testUpdateUser() throws ClassNotFoundException, UpdatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		testUser.setId(1);
		testUser.setSurname("Petrov");
		when(dao.update(any(Connection.class), any(User.class))).thenReturn(testUser);
		User user = new User();
		user.setName("Ivan");
		user.setSurname("Petrov");
		user.setEmail("ivan.petrov@bestemail.com");
		user.setPassword("12345");
		assertEquals(userManager.updateUser(user).getSurname(), "Petrov");
		
		when(dao.update(any(Connection.class), any(User.class))).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.updateUser(user).getSurname(), "Petrov");
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.updateUser(user).getSurname(), "Petrov");
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testSetUserBlock() throws ClassNotFoundException, UpdatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		testUser.setId(1);
		testUser.setSurname("Petrov");
		when(dao.findById(any(Connection.class), anyInt())).thenReturn(testUser);
		when(dao.setUserBlock(any(Connection.class), any(User.class), anyBoolean())).thenReturn(testUser);
		User user = new User();
		user.setId(1);
		user.setName("Ivan");
		user.setSurname("Petrov");
		user.setEmail("ivan.petrov@bestemail.com");
		user.setPassword("12345");
		assertEquals(userManager.setUserBlock(user.getId(), true).getId(), 1);
		
		when(dao.setUserBlock(any(Connection.class), any(User.class), anyBoolean())).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.setUserBlock(user.getId(), true).getId(), 1);
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.setUserBlock(user.getId(), true).getId(), 1);
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testSetCurrentLang() throws ClassNotFoundException, UpdatingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User testUser = new User();
		testUser.setId(1);
		testUser.setSurname("Petrov");
		User user = new User();
		user.setId(1);
		user.setName("Ivan");
		user.setSurname("Petrov");
		user.setEmail("ivan.petrov@bestemail.com");
		user.setPassword("12345");
		userManager.setUserCurrentLang(user, "uk");
		verify(dao, times(1)).setUserCurrentLang(dao.getConnection(),user, "uk");

		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			userManager.setUserCurrentLang(user, "uk");
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
	}	

	@Test
	public void testFindAllUsers() throws ClassNotFoundException, FindingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			User testUser = new User();
			users.add(testUser);
		}
		when(dao.findAll(any(Connection.class))).thenReturn(users);
		assertEquals(userManager.findAllUsers().size(), 4);
		
		when(dao.findAll(any(Connection.class))).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.findAllUsers().size(), 4);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.findAllUsers().size(), 4);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindAllMasters() throws ClassNotFoundException, FindingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			User testUser = new User();
			users.add(testUser);
		}
		when(dao.findAllMasters(any(Connection.class))).thenReturn(users);
		assertEquals(userManager.findAllMasters().size(), 4);
		when(dao.findAllMasters(any(Connection.class))).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.findAllMasters().size(), 4);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.findAllMasters().size(), 4);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindUserById() throws ClassNotFoundException, FindingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		User user = new User();
		user.setId(1);
		user.setName("Ivan");
		user.setSurname("Petrov");
		user.setEmail("ivan.petrov@bestemail.com");
		user.setPassword("12345");
		when(dao.findById(any(Connection.class), anyInt())).thenReturn(user);
		assertEquals(userManager.findUserbyID(1), user);
		when(dao.findById(any(Connection.class), anyInt())).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.findUserbyID(1), user);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.findUserbyID(1), user);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindUsersByConditions() throws ClassNotFoundException, FindingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			User testUser = new User();
			users.add(testUser);
		}
		when(dao.findAllByConditions(any(Connection.class), anyBoolean(), any(Role.class), anyString())).thenReturn(users);
		assertEquals(userManager.findUsersByConditions(true, Role.CLIENT, ""), users);
		when(dao.findAllByConditions(any(Connection.class), anyBoolean(), any(Role.class), anyString())).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.findUsersByConditions(true, Role.CLIENT, ""), users);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.findUsersByConditions(true, Role.CLIENT, ""), users);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
	}
	
	
	@Test
	public void testfindAllMastersByService() throws ClassNotFoundException, FindingUserException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		TreeMap<User, Integer> masters = new TreeMap<>(Comparator.comparing(User::getSurname));
		User user = new User();
		user.setId(1);
		user.setSurname("Ivanov");
		masters.put(user, 100);
		user = new User();
		user.setId(2);
		user.setSurname("Petrov");
		masters.put(user, 200);
		when(dao.findAllMastersByService(any(Connection.class), anyInt(), anyString())).thenReturn(masters);
		assertEquals(userManager.findAllMastersByService(2, "asc").size(), 2);
		when(dao.findAllMastersByService(any(Connection.class), anyInt(), anyString())).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.findAllMastersByService(2, "asc").size(), 2);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.findAllMasters().size(), 4);
			fail();
		} catch (FindingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testCreateUserWithServices() throws SQLException, CreatingUserException, ClassNotFoundException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		HashMap<Integer, Integer> services = new HashMap<>();
		services.put(1, 100);
		services.put(2, 200);
		services.put(3, 450);
		User testUser = new User();
		testUser.setName("Ivan");
		testUser.setSurname("Petrov");
		testUser.setEmail("ivan.petrov@bestemail.com");
		testUser.setPassword("12345");
		when(dao.save(any(Connection.class), any(User.class))).thenReturn(testUser);
		assertEquals(userManager.createUserWithServices(testUser, services), testUser);
		when(dao.save(any(Connection.class), any(User.class))).thenThrow(SQLException.class);
		try {
			assertEquals(userManager.createUserWithServices(testUser, services), testUser);
			fail();
		} catch (CreatingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.createUserWithServices(testUser, services), testUser);
			fail();
		} catch (CreatingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testAddServicesToUser() throws SQLException, ClassNotFoundException, UpdatingUserException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		HashMap<Integer, Integer> services = new HashMap<>();
		services.put(1, 100);
		services.put(2, 200);
		services.put(3, 450);
		User testUser = new User();
		testUser.setName("Ivan");
		testUser.setSurname("Petrov");
		testUser.setEmail("ivan.petrov@bestemail.com");
		testUser.setPassword("12345");
		userManager.addServicesToUser(testUser, services);
		verify(dao, times(1)).setServicesForMaster(dao.getConnection(), 1, services);
		doThrow(SQLException.class).when(dao).setServicesForMaster(isA(Connection.class), isA(Integer.class), isA(services.getClass()));
		try {
			userManager.addServicesToUser(testUser, services);
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(userManager.createUserWithServices(testUser, services), testUser);
			fail();
		} catch (CreatingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testDeleteServiceFromMaster() throws SQLException, ClassNotFoundException, UpdatingUserException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		Service service = new Service(1, "tratata", "tata");
		User testUser = new User();
		testUser.setName("Ivan");
		testUser.setSurname("Petrov");
		testUser.setEmail("ivan.petrov@bestemail.com");
		testUser.setPassword("12345");
		userManager.deleteServiceFromMaster(testUser, service);
		verify(dao, times(1)).deleteServiceFromMaster(dao.getConnection(), testUser, service);
		doThrow(SQLException.class).when(dao).deleteServiceFromMaster(isA(Connection.class), isA(User.class), isA(Service.class));
		try {
			userManager.deleteServiceFromMaster(testUser, service);
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			userManager.deleteServiceFromMaster(testUser, service);
			fail();
		} catch (UpdatingUserException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testGetInstanse() {
		UserManager manager1 = UserManager.getInstance();
		UserManager manager2 = UserManager.getInstance();
		
		assertEquals(manager1, manager2);
	}
}
