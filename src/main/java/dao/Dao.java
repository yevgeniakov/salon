package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
	    
	    T findById(Connection con, int id) throws SQLException;
	    
	    List<T> findAll(Connection con) throws SQLException;
	    
	    T save(Connection con, T t) throws SQLException;
	    
	    void delete(Connection con, T t) throws SQLException;
	}
