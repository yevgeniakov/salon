package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DBConnection;
import dao.impl.ServiceDao;
import entity.Service;

public class ServiceManager {
	private static ServiceManager instance;
	private ServiceDao dao;
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

	public Service findServiceByID(int id) throws Exception {
		logger.info("enter");

		Connection con = null;
		Service service = null;
		try {
			con = dao.getConnection();
			service = dao.findById(con, id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO throw new custom Exception
		} finally {
			DBConnection.close(con);
		}

		return service;
	}

	public Service findServiceByName(String name) {
		logger.info("enter");

		Connection con = null;
		Service service = null;
		try {
			con = dao.getConnection();
			service = dao.findByName(con, name);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO throw new custom Exception
		} finally {
			DBConnection.close(con);
		}
		return service;
	}

	public List<Service> findAllservices() throws Exception {
		logger.info("enter");

		Connection con = null;
		List<Service> services = new ArrayList<>();
		try {
			con = dao.getConnection();
			services = dao.findAll(con);
		} catch (Exception e) {
			// TODO throw new custom Exception
			logger.error(e.getMessage(), e);
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

	public TreeMap<Service, Integer> findAllServicesByMaster(int master_id) throws Exception {
		logger.info("enter");

		Connection con = null;
		TreeMap<Service, Integer> services = new TreeMap<>();
		try {
			con = dao.getConnection();
			services = dao.findAllbyMaster(con, master_id);
		} catch (Exception e) {
			// TODO throw new custom Exception
			logger.error(e.getMessage(), e);
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

	public Service createService(Service service) throws Exception {
		logger.info("enter");

		Connection con = null;

		try {
			con = dao.getConnection();
			service = dao.save(con, service);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			// TODO (2) throw your own exception
			throw new Exception("Cannot create service", e);
		} finally {
			DBConnection.close(con);
		}
		return service;
	}

	public List<Service> findAllServicesAbsentByMaster(int master_id) {
		logger.info("enter");

		Connection con = null;
		List<Service> services = new ArrayList<>();
		try {
			con = dao.getConnection();
			services = dao.findAllAbsentByMaster(con, master_id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

}
