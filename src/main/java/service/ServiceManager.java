package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.exceptions.CreatingServiceException;
import controller.exceptions.FindingServiceException;
import dao.DBConnection;
import dao.impl.ServiceDao;
import entity.Service;

/**
 * provides operations with Service: creating, updating and representing information
 * 
 * @author yevgenia.kovalova
 *
 */

public class ServiceManager {
	private static ServiceManager instance;
	private final ServiceDao dao;
	private static final Logger logger = LogManager.getLogger(ServiceManager.class);

	public static synchronized ServiceManager getInstance() {
		if (instance == null) {
			instance = new ServiceManager();
		}
		return instance;
	}

	private ServiceManager() {
		this.dao = ServiceDao.getInstance();
	}

	public ServiceManager(ServiceDao dao) {
		this.dao = dao;
	}

	public Service findServiceByID(int id) throws FindingServiceException {
		logger.trace("enter");

		Connection con = null;
		Service service;
		try {
			con = dao.getConnection();
			service = dao.findById(con, id);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingServiceException("Cannot find service by id: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}

		return service;
	}

	public Service findServiceByName(String name) throws FindingServiceException {
		logger.trace("enter");

		Connection con = null;
		Service service;
		try {
			con = dao.getConnection();
			service = dao.findByName(con, name);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingServiceException("Cannot find service by name: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return service;
	}

	public List<Service> findAllservices() throws FindingServiceException {
		logger.trace("enter");

		Connection con = null;
		List<Service> services;
		try {
			con = dao.getConnection();
			services = dao.findAll(con);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingServiceException("Cannot find all services: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

	public TreeMap<Service, Integer> findAllServicesByMaster(int master_id) throws FindingServiceException {
		logger.trace("enter");

		Connection con = null;
		TreeMap<Service, Integer> services;
		try {
			con = dao.getConnection();
			services = dao.findAllbyMaster(con, master_id);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingServiceException("Cannot find all services by master: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

	public Service createService(Service service) throws CreatingServiceException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			service = dao.save(con, service);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new CreatingServiceException("Cannot create service: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return service;
	}

	public List<Service> findAllServicesAbsentByMaster(int master_id) throws FindingServiceException {
		logger.trace("enter");

		Connection con = null;
		List<Service> services;
		try {
			con = dao.getConnection();
			services = dao.findAllAbsentByMaster(con, master_id);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingServiceException("Cannot find all services absent by master: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

}
