package service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DBConnection;
import dao.impl.UserDao;
import entity.Service;
import entity.User;
import service.utils.PasswordEncodingService;

public class UserManager {
	
	private static UserManager instance;
	private UserDao dao;
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
	
	
	
	public User findUserbyID(int id) throws Exception {
		Connection con = null;
		User user = null;
	    try {
	        con = dao.getConnection();
	        user =  dao.findById(con, id);
	        
	    } catch (Exception e) {
	    	
	    // TODO throw new custom Exception
	    } finally {
	        DBConnection.close(con);
	    }
		
	    return user;
	}
	
	

	public List<User> findAllUsers() throws Exception {
		Connection con = null;
		List<User> users = new ArrayList<>();
	    try {
	        con = dao.getConnection();
	        users =  dao.findAll(con);
	        
	    } catch (Exception e) {
	    	// TODO throw new custom Exception
	    } finally {
	        DBConnection.close(con);
	    }
		
	    return users;
	}
	
	public List<User> findAllMasters() throws Exception{
		System.out.println("#userService findAllMasters");
		Connection con = null;
		List<User> masters = new ArrayList<>();
	    try {
	        con = dao.getConnection();
	        masters =  dao.findAllMasters(con);
	        
	    } catch (Exception e) {
	    	// TODO throw new custom Exception
	    } finally {
	        DBConnection.close(con);
	    }
		
	    return masters;
	}
	
	public SortedMap<User, Integer> findAllMastersByService(int service_id, String sort) {
		Connection con = null;
		SortedMap<User, Integer> masters = null;
		
		
	    try {
	        con = dao.getConnection();
	        masters =  dao.findAllMastersByService(con, service_id, sort);
	        System.out.println(masters);
	    } catch (Exception e) {
	    	// TODO throw new custom Exception
	    	e.printStackTrace();
	    } finally {
	        DBConnection.close(con);
	    }
		
	    return masters;
	}

	
	public User checkCredentials(String email, String password) throws Exception {
		Connection con = null;
		User user;
		try {
			con = dao.getConnection();
			user = dao.findByEmail(con, email);
		} catch (SQLException ex) {
			// (1) write to log: log.error(..., ex);
			System.out.println("can't find user by email");
			
			// (2) throw your own exception
			throw new Exception("Cannot find user", ex);
		} finally {
			DBConnection.close(con);
		}
		
		if (user == null) {
			System.out.println("no such user");
			throw new Exception("no such user");
		}
		
		if (!PasswordEncodingService.validatePassword(password, user.getPassword())) {
			System.out.println("password is wrong");
			throw new Exception("password is wrong");
		}
		
		if (user.getIsBlocked()) {
			System.out.println("user is blocked");
			throw new Exception("user is blocked");
		}
		
		return user;
	}
	
	public User createUser(User user) throws Exception {
		Connection con = null;
		
		try {
			encodePassword(user);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new Exception("Cannot create user", e);
		}


		try {
			con = dao.getConnection(); 
			user = dao.save(con, user);

		} catch (SQLException e) {
			// (1) write to log: log.error(..., ex);
			e.printStackTrace();
			
			// TODO (2) throw your own exception
			throw new Exception("Cannot create user", e);
		} finally {
			DBConnection.close(con);
		}
		
		return user;
	}
	
	public User updateUser(User user) throws Exception {
		Connection con = null;
		
		try {
			con = dao.getConnection(); 
			user = dao.update(con, user);

		} catch (SQLException e) {
			// (1) write to log: log.error(..., ex);
			e.printStackTrace();
			
			// TODO (2) throw your own exception
			throw new Exception("Cannot update user", e);
		} finally {
			DBConnection.close(con);
		}
		
		return user;
	}



	public User setUserBlock(int id, boolean isBlocked) throws Exception {
		Connection con = null;
		User user = new User();
		try {
			con = dao.getConnection(); 
			
			user = dao.findById(con, id);
			user = dao.setUserBlock(con, user, isBlocked);

		} catch (SQLException e) {
			// (1) write to log: log.error(..., ex);
			e.printStackTrace();
			
			// TODO (2) throw your own exception
			throw new Exception("Cannot set user block", e);
		} finally {
			DBConnection.close(con);
		}
		
		return user;
	}


	private void encodePassword(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		user.setPassword(PasswordEncodingService.generateStorngPasswordHash(user.getPassword()));
		
	}

	public User createUserWithServices(User user, HashMap<Integer, Integer> serviceMap) throws Exception {
		Connection con = null;
		
		try {
			encodePassword(user);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new Exception("Cannot create user", e);
		}

		try {
			con = dao.getConnection(); 
			con.setAutoCommit(false);
			user = dao.save(con, user);
			dao.setServicesForMaster(con, user.getId(), serviceMap);
			con.commit();
		} catch (SQLException e) {
			// (1) write to log: log.error(..., ex);
			e.printStackTrace();
			con.rollback();
			// TODO (2) throw your own exception
			throw new Exception("Cannot create user", e);
		} finally {
			DBConnection.close(con);
		}
		
		return user;
	}

	public User addServicesToUser(User user, HashMap<Integer, Integer> serviceMap) throws Exception {
		Connection con = null;

		try {
			con = dao.getConnection();
			dao.setServicesForMaster(con, user.getId(), serviceMap);

		} catch (SQLException e) {
			// (1) write to log: log.error(..., ex);
			e.printStackTrace();
			
			// TODO (2) throw your own exception
			throw new Exception("Cannot create user", e);
		} finally {
			DBConnection.close(con);
		}

		return user;
	}

	public void deleteServiceFromMaster(User master, Service service) throws Exception  {
		Connection con = null;

		try {
			con = dao.getConnection();
			dao.deleteServiceFromMaster(con, master, service);

		} catch (SQLException e) {
			// (1) write to log: log.error(..., ex);
			e.printStackTrace();
			
			// TODO (2) throw your own exception
			throw new Exception("Cannot delete service from master", e);
		} finally {
			DBConnection.close(con);
		}

		
		
	}

	public List<User> findUsersByConditions(Boolean isBlocked, String searchValue) throws Exception {
		Connection con = null;
		List<User> users = new ArrayList<>();
	    try {
	        con = dao.getConnection();
	        users =  dao.findAllByConditions(con, isBlocked, searchValue);
	        
	    } catch (Exception e) {
	    	// TODO throw new custom Exception
	    } finally {
	        DBConnection.close(con);
	    }
		
	    return users;
	}


}
