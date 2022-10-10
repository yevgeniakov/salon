package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DBConnection;
import dao.Dao;
import entity.Service;

public class ServiceDao implements Dao<Service>{
private static ServiceDao instance;
private static final Logger logger = LogManager.getLogger(ServiceDao.class);

public static synchronized ServiceDao getInstance() {
	if (instance == null) {
		instance = new ServiceDao();
	}
	return instance;
}
	
	private static final String FIND_SERVICE_BY_ID = 
			"select * from services where id=?";
	
	private static final String FIND_SERVICE_BY_NAME = 
			"select * from services where name=?";

	private static final String INSERT_SERVICE = 
			"insert into services values (DEFAULT, ?, ?)";

	private static final String FIND_ALL_SERVICES = 
			"select * from services";
	private static final String FIND_ALL_SERVICES_BY_MASTER = 
			"select master_services.id AS master_services_id "
			+ ", master_services.service_id AS id "
			+ ", master_services.price AS price "
			+ ", services.info AS info "
			+ ", services.name AS name "
			+ "from master_services "
			+ "left join services "
			+ "on master_services.service_id = services.id  "
			+ "where master_id=?";
	private static final String FIND_ALL_SERVICES_ABSENT_BY_MASTER = 
			"select services.id AS id "
			+ ", services.name AS name "
			+ ", services.info AS info "
			+ ", master_services.id AS ms_id  "
			+ "from services "
			+ "left join master_services "
			+ "on services.id = master_services.service_id and master_services.master_id = ? "
			+ "where master_services.id is null";

	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		
		return DBConnection.getConnection();
	}
	


	@Override
	public Service findById(Connection con, int id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
        try { stmt = con.prepareStatement(FIND_SERVICE_BY_ID);
        	  stmt.setInt(1, id);
             rs = stmt.executeQuery(); 
            if (rs.next()) {
                return exstractService(rs);
            }
            return null;
            
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e); 
        } finally {
        	DBConnection.closeResultSet(rs);
        	DBConnection.closeStatement(stmt);
		}
	}

	@Override
	public List<Service> findAll(Connection con) {
		 List<Service> services = new ArrayList<>();
		 Statement stmt = null;
		 ResultSet rs = null;
         try {stmt = con.createStatement();
              rs = stmt.executeQuery(FIND_ALL_SERVICES); 
             while (rs.next()) {
            	 services.add(exstractService(rs));
             }
             return services;
         } catch (SQLException e) {
             throw new RuntimeException(e.getMessage(), e); 
         } finally {
        	 DBConnection.closeResultSet(rs);
        	 DBConnection.closeStatement(stmt);
		}
	}
	
	public TreeMap<Service, Integer> findAllbyMaster(Connection con, int master_id) {
		TreeMap<Service, Integer> services = new TreeMap<>();
		 PreparedStatement stmt = null;
		 ResultSet rs = null;
        try {stmt = con.prepareStatement(FIND_ALL_SERVICES_BY_MASTER);
        	stmt.setInt(1, master_id);
             rs = stmt.executeQuery(); 
            while (rs.next()) {
           	 services.put(exstractService(rs), rs.getInt("price"));
            }
            return services;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e); 
        } finally {
       	 DBConnection.closeResultSet(rs);
       	 DBConnection.closeStatement(stmt);
		}
	}
	
	public Service findByName(Connection con, String name) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(FIND_SERVICE_BY_NAME);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return exstractService(rs);
			}
			return null;

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}


	@Override
	public Service save(Connection con, Service t) {
		System.out.println("dao#servicesave");
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			stmt = con.prepareStatement(INSERT_SERVICE, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, t.getName());
			stmt.setString(2, t.getInfo());
			
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("save service failed");
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
	public void delete(Connection con, Service t) {
		// TODO Auto-generated method stub
		
	}
	

	
	private Service exstractService(ResultSet rs) throws SQLException {
		Service service = new Service();
		service.setId(rs.getInt("id"));
		service.setName(rs.getString("name"));
		service.setInfo(rs.getString("info"));
		
		return service;
	}



	public List<Service> findAllAbsentByMaster(Connection con, int master_id) {
		List<Service> services = new ArrayList<>();
		 PreparedStatement stmt = null;
		 ResultSet rs = null;
       try {stmt = con.prepareStatement(FIND_ALL_SERVICES_ABSENT_BY_MASTER);
       	stmt.setInt(1, master_id);
            rs = stmt.executeQuery(); 
           while (rs.next()) {
          	 services.add(exstractService(rs));
           }
           return services;
       } catch (SQLException e) {
           throw new RuntimeException(e.getMessage(), e); 
       } finally {
      	 DBConnection.closeResultSet(rs);
      	 DBConnection.closeStatement(stmt);
		}
	}









}
