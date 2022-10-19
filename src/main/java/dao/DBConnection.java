package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import controller.listeners.ContextListener;

public class DBConnection {

	private static final Logger logger = LogManager.getLogger(ContextListener.class);
	private static BasicDataSource ds = new BasicDataSource();

	static {
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/salondb");
		ds.setUsername("root");
		ds.setPassword("root");
		ds.setMinIdle(5);
		ds.setMaxIdle(10);
		ds.setDefaultAutoCommit(true);

	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static void close(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private DBConnection() {
	}
}