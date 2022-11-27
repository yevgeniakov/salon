package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DBConnection;
import dao.Dao;
import entity.Appointment;
import entity.Role;
import entity.Service;
import entity.User;
import service.utils.PropertiesService;

/**
 * DAO class for database interacting for appointment-related operations
 * 
 * @author yevgenia.kovalova
 *
 */

public class AppointmentDao implements Dao<Appointment> {
	private static AppointmentDao instance;
	private static final Logger logger = LogManager.getLogger(AppointmentDao.class);

	public static synchronized AppointmentDao getInstance() {
		if (instance == null) {
			instance = new AppointmentDao();
		}
		return instance;
	}

	private static final String GET_MASTER_SCHEDULE = getQueryForMasterShedule();
	private static final String GET_MASTER_FREE_SLOTS = "select numbers.n AS timeslot "
			+ ", appointments.user_id AS user_id " + "from numbers " + "left join appointments "
			+ "on numbers.n = timeslot and date = ? and master_id = ? " + "where appointments.user_id is null";
	private static final String CREATE_TABLE = "CREATE TEMPORARY TABLE numbers "
			+ "SELECT n FROM generator_23 WHERE n >= " + PropertiesService.getProperty("firstWorkHour") + " AND n <= "
			+ PropertiesService.getProperty("lastWorkHour");
	private static final String DROP_TABLE = "drop temporary table if exists numbers";
	private static final String FIND_APPOINTMENT_BY_KEY = getQueryForAppointment();
	private static final String FIND_APPOINTMENTS_LIST = getQueryForAppointmentList();
	private static final String GET_PRICE_FOR_APPOINTMENT = "select master_services.price from master_services where master_id=? and service_id=?";
	private static final String INSERT_APPOINTMENT = "insert into appointments values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_APPOINTMENT = "delete from appointments where date=? and timeslot=? and master_id=?";
	private static final String FIND_ALL_APPOINTMENTS = "select * from appointments";
	private static final String SET_ISPAID = "update appointments set isPaid=? where master_id=? and date=? and timeslot=?";
	private static final String SET_ISDONE = "update appointments set isDone=? where master_id=? and date=? and timeslot=?";
	private static final String SET_TIME = "update appointments set date=?, timeslot=? where master_id=? and date=? and timeslot=?";
	private static final String SET_FEEDBACK = "update appointments set feedback=? , rating= ? where master_id=? and date=? and timeslot=?";
	private static final String SET_MASTER_RATING = "update users set rating=? where id=?";
	private static final String CALCULATE_MASTER_RATING = "select master_id, CAST(AVG(CAST(rating as DECIMAL(10,2))) AS DECIMAL(10,2)) AS rating from appointments where master_id =? and rating > 0 group by master_id";

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		return DBConnection.getConnection();
	}
	
	/**
	* Returns the SQL query for receiving full appointment info
	* 
	*/
	private static String getQueryForAppointment() {
		return "select   " + "	appointments.date AS date  " + ", appointments.timeslot AS timeslot"
				+ "	, appointments.user_id AS user_id  " + "	, appointments.master_id AS master_id  "
				+ " , appointments.rating AS rating" + "	, appointments.service_id AS service_id   "
				+ "	, appointments.isdone AS isdone  " + "	, appointments.ispaid AS ispaid  "
				+ "	, appointments.sum AS sum  " + "	, appointments.feedback AS feedback  "
				+ "	, m.email AS master_email  " + "	, m.password AS master_password  " + "	, m.tel AS master_tel  "
				+ ", m.rating AS master_rating" + "	, m.name AS master_name  " + "	, m.surname AS master_surname  "
				+ "	, m.currentlang AS master_currentlang  " + "	, m.role AS master_role  "
				+ "	, m.info AS master_info  " + "	, m.isblocked AS master_isblocked  "
				+ "	, u.email AS user_email  " + "	, u.password AS user_password  " + "	, u.tel AS user_tel  "
				+ "	, u.name AS user_name  " + "	, u.surname AS user_surname  "
				+ "	, u.currentlang AS user_currentlang  " + "	, u.role AS user_role  " + "	, u.info AS user_info  "
				+ "	, u.isblocked AS user_isblocked  " + "	, services.name AS service_name  "
				+ "	, services.info AS service_info  " + "	from appointments  " + "	 " + "	left join users AS m  "
				+ "	on appointments.master_id = m.id  " + "	left join users AS u  "
				+ "	on appointments.user_id = u.id  " + "	left join services  "
				+ "	on appointments.service_id = services.id "
				+ "                where master_id=? and date=? and timeslot=?";
	}
	
	/**
	* Returns the SQL query for receiving full info about master's schedule on certain date
	* 
	*/
	private static String getQueryForMasterShedule() {
		return "select numbers.n AS timeslot " + ", ? AS date " + ", appointments.user_id AS user_id "
				+ ", ? AS master_id " + ", appointments.service_id AS service_id  " + ", appointments.isdone AS isdone "
				+ ", appointments.ispaid AS ispaid " + ", appointments.sum AS sum " + ", appointments.rating AS rating"
				+ ", appointments.feedback AS feedback " + ", m.email AS master_email "
				+ ", m.password AS master_password " + ", m.tel AS master_tel " + ", m.name AS master_name "
				+ ", m.rating AS master_rating" + ", m.surname AS master_surname "
				+ ", m.currentlang AS master_currentlang " + ", m.role AS master_role " + ", m.info AS master_info "
				+ ", m.isblocked AS master_isblocked " + ", u.email AS user_email " + ", u.password AS user_password "
				+ ", u.tel AS user_tel " + ", u.name AS user_name " + ", u.surname AS user_surname "
				+ ", u.currentlang AS user_currentlang " + ", u.role AS user_role " + ", u.info AS user_info "
				+ ", u.isblocked AS user_isblocked " + ", services.name AS service_name "
				+ ", services.info AS service_info " + "from numbers " + "left join appointments "
				+ "on numbers.n = timeslot and master_id = ? and date = ? " + "left join users AS m " + "on id = ? "
				+ "left join users AS u " + "on user_id = u.id " + "left join services "
				+ "on service_id = services.id ORDER BY timeslot";
	}
	
	/**
	* Returns the SQL query for receiving info about appointments within the specified date interval
	* 
	*/
	private static String getQueryForAppointmentList() {
		return "select   " + "	appointments.date AS date  " + ", appointments.timeslot AS timeslot"
				+ "	, appointments.user_id AS user_id  " + "	, appointments.master_id AS master_id  "
				+ " , appointments.rating AS rating" + "	, appointments.service_id AS service_id   "
				+ "	, appointments.isdone AS isdone  " + "	, appointments.ispaid AS ispaid  "
				+ "	, appointments.sum AS sum  " + "	, appointments.feedback AS feedback  "
				+ "	, m.email AS master_email  " + "	, m.password AS master_password  " + "	, m.tel AS master_tel  "
				+ ", m.rating AS master_rating" + "	, m.name AS master_name  " + "	, m.surname AS master_surname  "
				+ "	, m.currentlang AS master_currentlang  " + "	, m.role AS master_role  "
				+ "	, m.info AS master_info  " + "	, m.isblocked AS master_isblocked  "
				+ "	, u.email AS user_email  " + "	, u.password AS user_password  " + "	, u.tel AS user_tel  "
				+ "	, u.name AS user_name  " + "	, u.surname AS user_surname  "
				+ "	, u.currentlang AS user_currentlang  " + "	, u.role AS user_role  " + "	, u.info AS user_info  "
				+ "	, u.isblocked AS user_isblocked  " + "	, services.name AS service_name  "
				+ "	, services.info AS service_info  " + "	from appointments  " + "	 " + "	left join users AS m  "
				+ "	on appointments.master_id = m.id  " + "	left join users AS u  "
				+ "	on appointments.user_id = u.id  " + "	left join services  "
				+ "	on appointments.service_id = services.id " + "                where date >=? and date <=?";
	}

	public Appointment findByKey(Connection con, int master_id, LocalDate date, int timeslot) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(FIND_APPOINTMENT_BY_KEY);
			stmt.setInt(1, master_id);
			stmt.setDate(2, Date.valueOf(date));
			stmt.setInt(3, timeslot);

			try {
				rs = stmt.executeQuery();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e, master_id, date, timeslot);
				throw e;
			}
			if (rs.next()) {
				return extractAppointment(rs);
			}
			logger.trace("no result.", master_id, date, timeslot);
			return null;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}

	@Override
	public List<Appointment> findAll(Connection con) throws SQLException {
		logger.trace("enter");

		List<Appointment> appointments = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_APPOINTMENTS);
			while (rs.next()) {
				appointments.add(extractAppointment(rs));
			}
			return appointments;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}

	@Override
	public Appointment save(Connection con, Appointment t) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(INSERT_APPOINTMENT, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, Date.valueOf(t.getDate()));
			stmt.setInt(2, t.getTimeslot());
			stmt.setInt(3, t.getUser().getId());
			stmt.setInt(4, t.getMaster().getId());
			stmt.setInt(5, t.getService().getId());
			stmt.setBoolean(6, t.getIsDone());
			stmt.setBoolean(7, t.getIsPaid());
			stmt.setInt(8, t.getSum());
			stmt.setString(9, t.getFeedback());
			stmt.setDouble(10, t.getRating());
			stmt.executeUpdate();
			return t;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, t);
			throw e;
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}

	@Override
	public void delete(Connection con, Appointment t) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(DELETE_APPOINTMENT);
			stmt.setDate(1, Date.valueOf(t.getDate()));
			stmt.setInt(2, t.getTimeslot());
			stmt.setInt(3, t.getMaster().getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, t);
			throw e;
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}
	
	/**
	* Returns the Appointment entity from ResultSet after executing the query
	* 
	*/
	private Appointment extractAppointment(ResultSet rs) throws SQLException {
		logger.trace("enter");

		Appointment appointment = new Appointment();
		try {
			appointment.setTimeslot(Integer.parseInt(rs.getString("timeslot")));
			appointment.setDate(LocalDate.parse(rs.getDate("date").toString()));
			int master_id = rs.getInt("master_id");
			String master_email = rs.getString("master_email");
			String master_password = rs.getString("master_password");
			String master_name = rs.getString("master_name");
			String master_surname = rs.getString("master_surname");
			String master_tel = rs.getString("master_tel");
			double master_rating = rs.getDouble("master_rating");
			Role master_role = Role.HAIRDRESSER;
			String master_info = rs.getString("master_info");
			boolean master_isblocked = rs.getBoolean("master_isblocked");
			String master_currentLang = rs.getString("master_currentlang");
			User master = new User(master_id, master_email, master_password, master_name, master_surname, master_tel,
					master_role, master_info, master_isblocked, master_rating, master_currentLang);
			appointment.setMaster(master);
			int user_id = rs.getInt("user_id");
			if (user_id == 0) {
				return appointment;
			}
			String user_email = rs.getString("user_email");
			String user_password = rs.getString("user_password");
			String user_name = rs.getString("user_name");
			String user_surname = rs.getString("user_surname");
			String user_tel = rs.getString("user_tel");
			Role user_role = Role.CLIENT;
			String user_info = rs.getString("user_info");
			boolean user_isblocked = rs.getBoolean("user_isblocked");
			String user_currentLang = rs.getString("user_currentlang");
			User user = new User(user_id, user_email, user_password, user_name, user_surname, user_tel, user_role,
					user_info, user_isblocked, 0, user_currentLang);
			appointment.setUser(user);

			int service_id = rs.getInt("service_id");
			String service_name = rs.getString("service_name");
			String service_info = rs.getString("service_info");
			Service service = new Service(service_id, service_name, service_info);
			appointment.setService(service);
			appointment.setSum(rs.getInt("sum"));
			appointment.setFeedback(rs.getString("feedback"));
			appointment.setIsDone(rs.getBoolean("isdone"));
			appointment.setIsPaid(rs.getBoolean("ispaid"));
			appointment.setRating(rs.getDouble("rating"));
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return appointment;
	}

	public List<Appointment> getMasterSchedule(Connection con, LocalDate date, User master) throws SQLException {
		logger.trace("enter");

		List<Appointment> appointments = new ArrayList<>();
		PreparedStatement stmt = null;
		Statement stmt_create_table = null;
		Statement stmt_drop_table = null;
		ResultSet rs = null;
		try {
			stmt_drop_table = con.createStatement();
			stmt_drop_table.executeUpdate(DROP_TABLE);
			logger.trace("temp table dropped");
			stmt_create_table = con.createStatement();
			stmt_create_table.executeUpdate(CREATE_TABLE);
			logger.trace("temp table created");
			stmt = con.prepareStatement(GET_MASTER_SCHEDULE);
			stmt.setDate(1, Date.valueOf(date));
			stmt.setInt(2, master.getId());
			stmt.setInt(3, master.getId());
			stmt.setDate(4, Date.valueOf(date));
			stmt.setInt(5, master.getId());
			rs = stmt.executeQuery();
			logger.trace("query executed");
			stmt_drop_table.executeUpdate(DROP_TABLE);
			logger.trace("temp table dropped");
			while (rs.next()) {
				appointments.add(extractAppointment(rs));
			}
			return appointments;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, date, master.getId());
			throw e;
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
			DBConnection.closeStatement(stmt_drop_table);
			DBConnection.closeStatement(stmt_create_table);
		}
	}

	@Override
	public Appointment findById(Connection con, int id) {
		logger.trace("enter");

		throw new UnsupportedOperationException();
	}

	public int getPrice(Connection con, User master, Service service) throws SQLException {
		logger.trace("enter");

		int price = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(GET_PRICE_FOR_APPOINTMENT);
			stmt.setInt(1, master.getId());
			stmt.setInt(2, service.getId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				price = rs.getInt("price");
			} else {
				logger.trace("price is not got", master.getId(), service.getName());
			}
			return price;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, master.getId(), service.getName());
			throw e;
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}

	public void setIsPaid(Connection con, Appointment appointment, boolean isPaid) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(SET_ISPAID);
			stmt.setBoolean(1, isPaid);
			stmt.setInt(2, appointment.getMaster().getId());
			stmt.setDate(3, Date.valueOf(appointment.getDate()));
			stmt.setInt(4, appointment.getTimeslot());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, appointment.getMaster().getId(), appointment.getDate(),
					appointment.getTimeslot());
			throw e;
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}

	public void setIsDone(Connection con, Appointment appointment, boolean isDone) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(SET_ISDONE);
			stmt.setBoolean(1, isDone);
			stmt.setInt(2, appointment.getMaster().getId());
			stmt.setDate(3, Date.valueOf(appointment.getDate()));
			stmt.setInt(4, appointment.getTimeslot());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, appointment.getMaster().getId(), appointment.getDate(),
					appointment.getTimeslot(), isDone);
			throw e;
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}

	public void setFeedback(Connection con, Appointment appointment, String feedback, double rating)
			throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(SET_FEEDBACK);
			stmt.setString(1, feedback);
			stmt.setDouble(2, rating);
			stmt.setInt(3, appointment.getMaster().getId());
			stmt.setDate(4, Date.valueOf(appointment.getDate()));
			stmt.setInt(5, appointment.getTimeslot());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, appointment.getMaster().getId(), appointment.getDate(),
					appointment.getTimeslot(), feedback, rating);
			throw e;
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}

	public void setMasterRating(Connection con, User master, double rating) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(SET_MASTER_RATING);
			stmt.setDouble(1, rating);
			stmt.setInt(2, master.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, master.getId(), rating);
			throw e;
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}
	
	/**
	* Calculating master's rating as average rating from all received feedbacks
	* 
	*/
	public double calculateMasterRating(Connection con, User master) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		double rating = 0;
		try {
			stmt = con.prepareStatement(CALCULATE_MASTER_RATING);
			stmt.setInt(1, master.getId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				rating = rs.getDouble("rating");
				logger.trace("average rating ", master.getId(), rating);
			}
			return rating;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, master.getId());
			throw e;
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}
	
	/**
	* Returns the Appointment list that matches provided conditions.
	* In case parameter is null - is ignored, otherwise is applied
	*/
	public List<Appointment> findByConditions(Connection con, LocalDate dateFrom, LocalDate dateTo, Integer master_id,
			Integer user_id, Integer service_id, Boolean isDone, Boolean isPaid, Boolean isRating) throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Appointment> appointments = new ArrayList<>();
		String sql = FIND_APPOINTMENTS_LIST;
		sql = addConditionsToSQL(sql);
		try {
			stmt = con.prepareStatement(sql);
			int i = 0;
			stmt.setDate(++i, Date.valueOf(dateFrom));
			stmt.setDate(++i, Date.valueOf(dateTo));
			stmt.setInt(++i, (master_id == null) ? 0 : master_id);
			stmt.setBoolean(++i, (master_id == null));
			stmt.setInt(++i, (user_id == null) ? 0 : user_id);
			stmt.setBoolean(++i, (user_id == null));
			stmt.setInt(++i, (service_id == null) ? 0 : service_id);
			stmt.setBoolean(++i, (service_id == null));
			stmt.setBoolean(++i, isDone != null && isDone);
			stmt.setBoolean(++i, (isDone == null));
			stmt.setBoolean(++i, isPaid != null && isPaid);
			stmt.setBoolean(++i, (isPaid == null));
			stmt.setBoolean(++i, isRating == null || !isRating);
			stmt.setBoolean(++i, isRating == null || isRating);
			rs = stmt.executeQuery();
			while (rs.next()) {
				appointments.add(extractAppointment(rs));
			}
			return appointments;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, dateFrom, dateTo, master_id, user_id, service_id, isDone, isPaid, isRating);
			throw e;
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}
	
	/**
	* Complements the SQL query for appointments list with conditions
	* 
	*/
	private String addConditionsToSQL(String sql) {
		logger.trace("enter");

		StringBuilder str = new StringBuilder(sql);
		str.append(" and (master_id=? or ?)").append(" and (user_id=? or ?)").append(" and (service_id=? or ?)")
				.append(" and (isDone=? or ?)").append(" and (isPaid=? or ?)")
				.append(" and (appointments.rating>0 or ?)").append(" and (appointments.rating=0 or ?)")
				.append(" order by appointments.date, appointments.timeslot");

		logger.trace(str);
		return str.toString();
	}

	public List<Integer> getMasterFreeSlots(Connection con, LocalDate date, User master) throws SQLException {
		logger.trace("enter");

		List<Integer> freeSlots = new ArrayList<>();
		PreparedStatement stmt = null;
		Statement stmt_create_table = null;
		Statement stmt_drop_table = null;
		ResultSet rs = null;
		try {
			stmt_drop_table = con.createStatement();
			stmt_drop_table.executeUpdate(DROP_TABLE);
			stmt_create_table = con.createStatement();
			stmt_create_table.executeUpdate(CREATE_TABLE);
			stmt = con.prepareStatement(GET_MASTER_FREE_SLOTS);
			stmt.setDate(1, Date.valueOf(date));
			stmt.setInt(2, master.getId());
			rs = stmt.executeQuery();
			stmt_drop_table.executeUpdate(DROP_TABLE);
			while (rs.next()) {
				freeSlots.add(rs.getInt("timeslot"));
			}
			return freeSlots;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, date, master.getId());
			throw e;
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
			DBConnection.closeStatement(stmt_drop_table);
			DBConnection.closeStatement(stmt_create_table);
		}
	}

	public void setTime(Connection con, Appointment appointment, LocalDate newDate, Integer newTimeslot)
			throws SQLException {
		logger.trace("enter");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(SET_TIME);
			stmt.setDate(1, Date.valueOf(newDate));
			stmt.setInt(2, newTimeslot);
			stmt.setInt(3, appointment.getMaster().getId());
			stmt.setDate(4, Date.valueOf(appointment.getDate()));
			stmt.setInt(5, appointment.getTimeslot());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, appointment.getMaster().getId(), appointment.getDate(),
					appointment.getTimeslot(), newDate, newTimeslot);
			throw e;
		} finally {
			DBConnection.closeStatement(stmt);
		}
	}
}
