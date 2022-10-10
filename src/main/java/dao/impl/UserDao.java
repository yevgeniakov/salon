package dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DBConnection;
import dao.Dao;
import entity.Role;
import entity.Service;
import entity.User;

public class UserDao implements Dao<User> {
	private static UserDao instance;
	private static final Logger logger = LogManager.getLogger(UserDao.class);
	
	public static synchronized UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	private static final String FIND_USER_BY_ID = 
			"select * from users where id=?";
	
	private static final String FIND_USER_BY_EMAIL = 
			"select * from users where email=?";

	private static final String INSERT_USER = 
			"insert into users values (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String FIND_ALL_USERS = 
			"select * from users";

	private static final String FIND_ALL_MASTERS =
			"select * from users where role=" + "'" + Role.HAIRDRESSER + "'";
	
	private static final String FIND_ALL_MASTERS_BY_SERVICES =
			"select master_services.id AS master_services_id "
					+ ", master_services.master_id AS master_service_id "
					+ ", master_services.price AS price "
					+ ", users.id AS id "
					+ ", users.info AS info "
					+ ", users.name AS name "
					+ ", users.surname AS surname "
					+ ", users.tel AS tel "
					+ ", users.email AS email "
					+ ", users.password AS password "
					+ ", users.currentlang AS currentlang "
					+ ", users.role AS role "
					+ ", users.isBlocked AS isBlocked "
					+ ", users.rating AS rating "
					+ "from master_services "
					+ "left join users "
					+ "on master_services.master_id = users.id "
					+ "where service_id=? AND users.role=" + "'" + Role.HAIRDRESSER + "'";

	private static final String UPDATE_USER =
			"update users set name=?, surname=?, tel=?, email=?, currentlang=?, info=?, isblocked=? where id=?";
	
	private static final String SET_USER_BLOCK = 
			"update users set isblocked=? where id=?";
	
	private static final String DELETE_SERVICE = 
			"delete from master_services where master_id=? and service_id=?";

	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		
		return DBConnection.getConnection();
	}
	
	
	@Override
	public User findById(Connection con, int id) {
		logger.info("UserDao#findById");
		PreparedStatement stmt = null;
		ResultSet rs = null;
        try { stmt = con.prepareStatement(FIND_USER_BY_ID);
        	  stmt.setInt(1, id);
             rs = stmt.executeQuery(); 
            if (rs.next()) {
                return exstractUser(rs);
            }
            return null;
            
        } catch (SQLException e) {
        	logger.error("UserDao#findById failed", e);
            throw new RuntimeException(e.getMessage(), e); 
        } finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}

	@Override
	public List<User> findAll(Connection con) {
		 List<User> users = new ArrayList<>();
		 Statement stmt = null;
		 ResultSet rs = null;
         try {stmt = con.createStatement();
              rs = stmt.executeQuery(FIND_ALL_USERS); 
             while (rs.next()) {
                 users.add(exstractUser(rs));
             }
             return users;
         } catch (SQLException e) {
             throw new RuntimeException(e.getMessage(), e); 
         } finally {
        	 DBConnection.closeResultSet(rs);
        	 DBConnection.closeStatement(stmt);
		}
	}


	@Override
	public User save(Connection con, User t) {
		System.out.println("dao#usersave");
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, t.getEmail());
			stmt.setString(2, t.getPassword());
			stmt.setString(3, t.getTel());
			stmt.setString(4, t.getName());
			stmt.setString(5, t.getSurname());
			stmt.setString(6, "en");
			stmt.setString(7, t.getRole().name().toLowerCase());
			stmt.setString(8, t.getInfo());
			stmt.setBoolean(9, false);
			stmt.setDouble(10, 0);
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("save user failed");
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		if (rows == 1) {
			try {
				ResultSet keys = stmt.getGeneratedKeys();
				while (keys.next()) {
					t.setId(keys.getInt(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBConnection.closeStatement(stmt);
			}
		}
		return t;
	}

	@Override
	public void delete(Connection con, User t) {
		// TODO Auto-generated method stub
		
	}
	
	public List<User> findAllMasters(Connection con) {
		System.out.println("#userdao findAllMasters");
		System.out.println(FIND_ALL_MASTERS);
		List<User> masters = new ArrayList<>();
		 Statement stmt = null;
		 ResultSet rs = null;
        try {stmt = con.createStatement();
             rs = stmt.executeQuery(FIND_ALL_MASTERS); 
            while (rs.next()) {
            	masters.add(exstractUser(rs));
            }
            return masters;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e); 
        } finally {
        	DBConnection.closeResultSet(rs);
        	DBConnection.closeStatement(stmt);
		}
	}
	
	public SortedMap<User, Integer> findAllMastersByService(Connection con, int service_id, String sort) {
		SortedMap<User, Integer> masters;
		
		if ("rating".equals(sort)) {
			masters = new TreeMap<>(Comparator.comparing(User::getRating).reversed().thenComparing(User::getSurname));
		} else {
			masters = new TreeMap<>(Comparator.comparing(User::getSurname));
		}
			
		 PreparedStatement stmt = null;
		 ResultSet rs = null;
       try {stmt = con.prepareStatement(FIND_ALL_MASTERS_BY_SERVICES);
       	stmt.setInt(1, service_id);
            rs = stmt.executeQuery(); 
           while (rs.next()) {
        	   System.out.println("putting...");
        	   masters.put(exstractUser(rs), rs.getInt("price"));
           }
           System.out.println(masters);
           return masters;
       } catch (SQLException e) {
           throw new RuntimeException(e.getMessage(), e); 
       } finally {
      	 DBConnection.closeResultSet(rs);
      	 DBConnection.closeStatement(stmt);
		}
	}

	
	public User findByEmail(Connection con, String email) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(FIND_USER_BY_EMAIL);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return exstractUser(rs);
			}
			return null;

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}
	
	public User update(Connection con, User t) {
		System.out.println("dao#usersave");
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(UPDATE_USER);
			stmt.setString(1, t.getName());
			stmt.setString(2, t.getSurname());
			stmt.setString(3, t.getTel());
			stmt.setString(4, t.getEmail());
			stmt.setString(5, "en");
			stmt.setString(6, t.getInfo());
			stmt.setBoolean(7, t.getIsBlocked());
			stmt.setInt(8, t.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update user failed");
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return t;
	}
	
	private User exstractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setName(rs.getString("name"));
		user.setSurname(rs.getString("surname"));
		user.setTel(rs.getString("tel"));
		user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
		user.setInfo(rs.getString("info"));
		user.setBlocked(rs.getBoolean("isblocked"));
		user.setRating(rs.getDouble("rating"));
		return user;
	}


	public User setUserBlock(Connection con, User t, boolean isBlocked) {
		System.out.println("dao#usersave");
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(SET_USER_BLOCK);
			stmt.setBoolean(1, isBlocked);
			stmt.setInt(2, t.getId());
			
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update user failed");
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		t.setBlocked(isBlocked);
		return t;
	}


	public void setServicesForMaster(Connection con, int id, HashMap<Integer, Integer> serviceMap) {
		System.out.println("dao#usersetservices");
		Statement stmt = null;
		String sql = getSqlForMasterServices(id, serviceMap);
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("setting services failed");
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		
	}


	private String getSqlForMasterServices(int id,  HashMap<Integer, Integer> serviceMap) {
		StringBuilder sql = new StringBuilder("INSERT INTO master_services VALUES ");
		int i = 0;
		for (HashMap.Entry<Integer, Integer> entry : serviceMap.entrySet()) {
		    Integer service_id = entry.getKey();
		    Integer price = entry.getValue();
		    if (i != 0) {
		    	sql.append(", ");
		    }	
		    sql.append("(DEFAULT, ").append(id).append(", ").append(service_id).append(", ").append(price).append(")");
		    i++;
		}
		System.out.println(sql);
		return sql.toString();
			
		}


	public void deleteServiceFromMaster(Connection con, User master, Service service) {
		System.out.println("dao#userdeleteservice");
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(DELETE_SERVICE);
			stmt.setInt(1, master.getId());
			stmt.setInt(2, service.getId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("delete service failed");
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		
				
	}


	public List<User> findAllByConditions(Connection con, Boolean isBlocked, String searchValue) {
		System.out.println("dao#usersByConditions");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();
		String sql = FIND_ALL_USERS;
		
		sql = addConditionsToSQL(sql);
		
		try {
			stmt = con.prepareStatement(sql);
			
			System.out.println(sql);

			int i = 0;
			
			stmt.setBoolean(++i, (isBlocked == null) ? false : isBlocked);
			stmt.setBoolean(++i, (isBlocked == null));
			
			stmt.setBoolean(++i, (searchValue == null));
			stmt.setString(++i, "%" + (searchValue == null ? "" : searchValue) + "%");
			stmt.setString(++i, "%" + (searchValue == null ? "" : searchValue) + "%");
			stmt.setString(++i, "%" + (searchValue == null ? "" : searchValue) + "%");
			stmt.setString(++i, "%" + (searchValue == null ? "" : searchValue) + "%");
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				
				users.add(exstractUser(rs));
			}
			return users;

		} catch (SQLException e) {
			System.out.println("getting list of users by condition failed");
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}
			
	private String addConditionsToSQL(String sql) {
		StringBuilder str = new StringBuilder(sql);
		
		str.append(" where ")
		.append("(isBlocked=? or ?)")
		.append(" and (? or (users.name like ? or users.surname like ? or users.email like ? or users.tel like ? ))");
		
		return str.toString();
	}	
		
	}



