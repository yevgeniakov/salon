package dao;

import java.sql.Connection;
import java.util.List;

public interface Dao<T> {
	    
	    T findById(Connection con, int id);
	    
	    List<T> findAll(Connection con);
	    
	    T save(Connection con, T t);
	    
	    void delete(Connection con, T t);
	}
