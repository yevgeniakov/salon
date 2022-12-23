package service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.exceptions.CheckCredentialsException;
import controller.exceptions.CreatingUserException;
import controller.exceptions.FindingUserException;
import controller.exceptions.UpdatingUserException;
import dao.DBConnection;
import dao.impl.UserDao;
import entity.Role;
import entity.Service;
import entity.User;
import service.utils.PasswordEncodingService;

/**
 * provides operations with User: creating, updating and representing
 * information
 * 
 * @author yevgenia.kovalova
 *
 */

public class UserManager {

	private static UserManager instance;
	private final UserDao dao;
	private static final Logger logger = LogManager.getLogger(UserManager.class);

	public static synchronized UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	private UserManager() {
		this.dao = UserDao.getInstance();
	}

	public UserManager(UserDao dao) {
		this.dao = dao;
	}

	public User findUserbyID(int id) throws FindingUserException {
		logger.trace("enter");

		Connection con = null;
		User user;
		try {
			con = dao.getConnection();
			user = dao.findById(con, id);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingUserException("Cannot find users by id: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return user;
	}

	public List<User> findAllUsers() throws FindingUserException {
		logger.trace("enter");

		Connection con = null;
		List<User> users;
		try {
			con = dao.getConnection();
			users = dao.findAll(con);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingUserException("Cannot find all users: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return users;
	}

	public List<User> findAllMasters() throws FindingUserException {
		logger.trace("enter");

		Connection con = null;
		List<User> masters;
		try {
			con = dao.getConnection();
			masters = dao.findAllMasters(con);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingUserException("Cannot find all masters: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return masters;
	}

	public SortedMap<User, Integer> findAllMastersByService(int service_id, String sort) throws FindingUserException {
		logger.trace("enter");

		Connection con = null;
		SortedMap<User, Integer> masters;
		try {
			con = dao.getConnection();
			masters = dao.findAllMastersByService(con, service_id, sort);
		} catch (SQLException | ClassNotFoundException e) {

			logger.error(e.getMessage(), e);
			throw new FindingUserException("Cannot find all masters By service: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return masters;
	}

	public User checkCredentials(String email, String password) throws CheckCredentialsException, FindingUserException {
		logger.trace("enter");

		Connection con = null;
		User user;
		try {
			con = dao.getConnection();
			user = dao.findByEmail(con, email);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingUserException("Cannot find user: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		if (user == null) {
			logger.info("user login failed. No such user", email);
			throw new CheckCredentialsException("no such user");
		}
		try {
			if (!PasswordEncodingService.validatePassword(password, user.getPassword())) {
				logger.info("user login failed. wrong password", email);
				throw new CheckCredentialsException("password is wrong");
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.info("user login failed. problem with password encoding", email);
			throw new CheckCredentialsException("problem with password encoding");

		}
		if (user.getIsBlocked()) {
			logger.info("user login failed. User is blocked. " + email);
			throw new CheckCredentialsException("user is blocked");
		}
		return user;
	}

	public User createUser(User user) throws CreatingUserException {
		logger.trace("enter");

		Connection con = null;
		try {
			encodePassword(user);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new CreatingUserException("Cannot create user: " + e.getMessage());
		}
		try {
			con = dao.getConnection();
			User exUser = dao.findByEmail(con, user.getEmail());
			if (exUser != null) {
				throw new CreatingUserException("Cannot create user: " + "this e-mail is already registered!");
			}
			user = dao.save(con, user);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new CreatingUserException("Cannot create user: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return user;
	}

	public User updateUser(User user) throws UpdatingUserException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			user = dao.update(con, user);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingUserException("Cannot update user" + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return user;
	}

	public User setUserBlock(int id, boolean isBlocked) throws UpdatingUserException, UpdatingUserException {
		logger.trace("enter");

		Connection con = null;
		User user = new User();
		try {
			con = dao.getConnection();
			user = dao.findById(con, id);
			user = dao.setUserBlock(con, user, isBlocked);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingUserException("Cannot set user block" + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return user;
	}

	private void encodePassword(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		logger.trace("enter");

		logger.trace("password is", user.getPassword());
		user.setPassword(PasswordEncodingService.generateStorngPasswordHash(user.getPassword()));
		logger.trace("password set", user.getPassword());
	}

	public User createUserWithServices(User user, HashMap<Integer, Integer> serviceMap) throws CreatingUserException {
		logger.trace("enter");

		Connection con = null;
		try {
			encodePassword(user);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.error(e.getMessage(), e);
			throw new CreatingUserException("Cannot create user: " + e.getMessage());
		}
		try {
			con = dao.getConnection();
			User exUser = dao.findByEmail(con, user.getEmail());
			if (exUser != null) {
				throw new CreatingUserException("Cannot create user: " + "this e-mail is already registered!");
			}
			con.setAutoCommit(false);
			user = dao.save(con, user);
			dao.setServicesForMaster(con, user.getId(), serviceMap);
			con.commit();
			logger.trace("transaction commited");
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e, "transaction cancelled");
			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException e1) {
				logger.error(e.getMessage(), e, "transaction cancelling failed");
				throw new CreatingUserException("Cannot create user: " + e1.getMessage());
			}
			throw new CreatingUserException("Cannot create user: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return user;
	}

	public User addServicesToUser(User user, HashMap<Integer, Integer> serviceMap) throws UpdatingUserException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.setServicesForMaster(con, user.getId(), serviceMap);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingUserException("Cannot add services to user: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return user;
	}

	public void deleteServiceFromMaster(User master, Service service) throws UpdatingUserException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.deleteServiceFromMaster(con, master, service);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingUserException("Cannot delete service from master: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}

	public List<User> findUsersByConditions(Boolean isBlocked, Role role, String searchValue)
			throws FindingUserException {
		logger.trace("enter");

		Connection con = null;
		List<User> users;
		try {
			con = dao.getConnection();
			users = dao.findAllByConditions(con, isBlocked, role, searchValue);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingUserException("Cannot find users by conditions: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return users;
	}

	public void setUserCurrentLang(User user, String locale) throws UpdatingUserException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			user = dao.findById(con, user.getId());
			user = dao.setUserCurrentLang(con, user, locale);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingUserException("Cannot set user locale" + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}
}
