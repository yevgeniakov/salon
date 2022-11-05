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

import controller.exceptions.CreatingServiceException;
import controller.exceptions.FindingServiceException;
import dao.impl.ServiceDao;
import entity.Role;
import entity.Service;
import service.ServiceManager;

public class ServiceManagerTest {
	@Mock
	private ServiceDao dao;
	@InjectMocks
	private ServiceManager serviceManager;

	@Before
	public void setUp() {
		this.dao = mock(ServiceDao.class);
		this.serviceManager = new ServiceManager(dao);
	}

	@Test
	public void testAddService() throws ClassNotFoundException, SQLException, CreatingServiceException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		Service testService = new Service(1, "tatta", "tatata tata ta");
		when(dao.save(any(Connection.class), any(Service.class))).thenReturn(testService);
		Service service = new Service();
		service.setId(1);
		service.setName("Tratata");
		service.setInfo("Trata tata taaa");
		
		assertEquals(serviceManager.createService(service).getId(), 1);
		
		when(dao.save(any(Connection.class), any(Service.class))).thenThrow(SQLException.class);
		try {
			assertEquals(serviceManager.createService(service).getId(), 1);
			fail();
		} catch (CreatingServiceException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(serviceManager.createService(service).getId(), 1);
			fail();
		} catch (CreatingServiceException e) {
			assertNotNull(e);
		}

	}

	

	@Test
	public void testFindAllServices() throws ClassNotFoundException, FindingServiceException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		List<Service> services = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Service testService = new Service();
			services.add(testService);
		}
		when(dao.findAll(any(Connection.class))).thenReturn(services);
		assertEquals(serviceManager.findAllservices().size(), 4);
		
		when(dao.findAll(any(Connection.class))).thenThrow(SQLException.class);
		try {
			assertEquals(serviceManager.findAllservices().size(), 4);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(serviceManager.findAllservices().size(), 4);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindAllServicesByMasters() throws ClassNotFoundException, SQLException, FindingServiceException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		TreeMap<Service, Integer> services = new TreeMap<>();
		services.put(new Service(1, "fsfsf", "hfgfgf"), 100);
		services.put(new Service(2, "fnnf", "hbvnfgf"), 200);
		services.put(new Service(3, "fsnf", "hbvnbvfgf"), 450);
		when(dao.findAllbyMaster(any(Connection.class), anyInt())).thenReturn(services);
		assertEquals(serviceManager.findAllServicesByMaster(3).size(), 3);
		when(dao.findAllbyMaster(any(Connection.class), anyInt())).thenThrow(SQLException.class);
		try {
			assertEquals(serviceManager.findAllServicesByMaster(3).size(), 3);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(serviceManager.findAllServicesByMaster(3).size(), 3);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindServiceById() throws ClassNotFoundException, FindingServiceException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		Service service = new Service();
		service.setId(1);
		service.setName("Immgm");
		service.setInfo("hghm jg hjg jghj");

		when(dao.findById(any(Connection.class), anyInt())).thenReturn(service);
		assertEquals(serviceManager.findServiceByID(1), service);
		when(dao.findById(any(Connection.class), anyInt())).thenThrow(SQLException.class);
		try {
			assertEquals(serviceManager.findServiceByID(1), service);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(serviceManager.findServiceByID(1), service);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindServiceByName() throws ClassNotFoundException, FindingServiceException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		Service service = new Service();
		service.setId(1);
		service.setName("Immgm");
		service.setInfo("hghm jg hjg jghj");

		when(dao.findByName(any(Connection.class), anyString())).thenReturn(service);
		assertEquals(serviceManager.findServiceByName("Immgm"), service);
		when(dao.findByName(any(Connection.class), anyString())).thenThrow(SQLException.class);
		try {
			assertEquals(serviceManager.findServiceByName("Immgm"), service);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(serviceManager.findServiceByName("Immgm"), service);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testFindServicesAbsentByMaster() throws ClassNotFoundException, FindingServiceException, SQLException {
		when(dao.getConnection()).thenReturn(mock(Connection.class));
		List<Service> services = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Service testService = new Service();
			services.add(testService);
		}
		when(dao.findAllAbsentByMaster(any(Connection.class), anyInt())).thenReturn(services);
		assertEquals(serviceManager.findAllServicesAbsentByMaster(3), services);
		when(dao.findAllAbsentByMaster(any(Connection.class), anyInt())).thenThrow(SQLException.class);
		try {
			assertEquals(serviceManager.findAllServicesAbsentByMaster(3), services);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
		when(dao.getConnection()).thenThrow(ClassNotFoundException.class);
		try {
			assertEquals(serviceManager.findAllServicesAbsentByMaster(3), services);
			fail();
		} catch (FindingServiceException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testGetInstanse() {
		ServiceManager manager1 = ServiceManager.getInstance();
		ServiceManager manager2 = ServiceManager.getInstance();
		
		assertEquals(manager1, manager2);
	}
}
