package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
			+ ", appointments.user_id AS user_id "
			+ "from numbers "
			+ "left join appointments "
			+ "on numbers.n = timeslot and date = ? and master_id = ? "
			+ "where appointments.user_id is null";

	private static final String CREATE_TABLE = "CREATE TEMPORARY TABLE numbers "
			+ "SELECT n FROM generator_23 WHERE n >= " + PropertiesService.getProperty("firstWorkHour") + " AND n <= "
			+ PropertiesService.getProperty("lastWorkHour");

	private static final String DROP_TABLE = "drop temporary table if exists numbers";

	private static final String FIND_APPOINTMENT_BY_KEY = getQueryForAppointment();
	
	private static final String FIND_APPOINTMENTS_LIST = getQueryForAppointmentList();
	
	private static final String GET_PRICE_FOR_APPOINTMENT = "select master_services.price from master_services where master_id=? and service_id=?";

	private static final String INSERT_APPOINTMENT = "insert into appointments values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

	private static String getQueryForAppointment() {
		return "select   " + "	appointments.date AS date  " + ", appointments.timeslot AS timeslot"
				+ "	, appointments.user_id AS user_id  " + "	, appointments.master_id AS master_id  "
				+ " , appointments.rating AS rating" + "	, appointments.service_id AS service_id   "
				+ "	, appointments.isdone AS isdone  " + "	, appointments.ispaid AS ispaid  "
				+ "	, appointments.sum AS sum  " + "	, appointments.feedback AS feedback  "
				+ "	, m.email AS master_email  "
				+ "	, m.password AS master_password  " + "	, m.tel AS master_tel  " + ", m.rating AS master_rating"
				+ "	, m.name AS master_name  " + "	, m.surname AS master_surname  "
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
	
	private static String getQueryForAppointmentList() {
		return "select   " + "	appointments.date AS date  " + ", appointments.timeslot AS timeslot"
				+ "	, appointments.user_id AS user_id  " + "	, appointments.master_id AS master_id  "
				+ " , appointments.rating AS rating" + "	, appointments.service_id AS service_id   "
				+ "	, appointments.isdone AS isdone  " + "	, appointments.ispaid AS ispaid  "
				+ "	, appointments.sum AS sum  " + "	, appointments.feedback AS feedback  "
				+ "	, m.email AS master_email  "
				+ "	, m.password AS master_password  " + "	, m.tel AS master_tel  " + ", m.rating AS master_rating"
				+ "	, m.name AS master_name  " + "	, m.surname AS master_surname  "
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
				+ "                where date >=? and date <=?";
	}

	public Appointment findByKey(Connection con, int master_id, LocalDate date, int timeslot) throws SQLException {
		logger.info("enter");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(FIND_APPOINTMENT_BY_KEY);
			System.out.println("params to query:");
			System.out.println(master_id);
			System.out.println(date);
			System.out.println(timeslot);
			stmt.setInt(1, master_id);
			stmt.setDate(2, Date.valueOf(date));
			stmt.setInt(3, timeslot);
			System.out.println("try to execute query...");
			try {
				rs = stmt.executeQuery();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				// TODO
			}
			if (rs.next()) {
				return exstractAppointment(rs);
			}
			logger.info("no result." + " master_id: " + master_id + " date: " + date + " timeslot: " + timeslot);
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
	public List<Appointment> findAll(Connection con) {
		logger.info("enter");
		
		List<Appointment> appointments = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_APPOINTMENTS);
			while (rs.next()) {
				appointments.add(exstractAppointment(rs));
			}
			return appointments;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}

	@Override
	public Appointment save(Connection con, Appointment t) {
		logger.info("enter");
		
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
			stmt.setString(11, t.getDate().toString() + t.getTimeslot() + t.getMaster().getId());
			stmt.executeUpdate();

			return t;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public void delete(Connection con, Appointment t) {
		logger.info("enter");
		
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(DELETE_APPOINTMENT);
			stmt.setDate(1, Date.valueOf(t.getDate()));
			stmt.setInt(2, t.getTimeslot());
			stmt.setInt(3, t.getMaster().getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	private Appointment exstractAppointment(ResultSet rs) throws SQLException {
		logger.info("enter");
		
		Appointment appointment = new Appointment();
		try {

			appointment.setTimeslot(Integer.parseInt(rs.getString("timeslot")));
			appointment.setDate(LocalDate.parse(rs.getDate("date").toString()));

			int master_id = rs.getInt("master_id");

			appointment.setSum(rs.getInt("sum"));
			appointment.setFeedback(rs.getString("feedback"));
			appointment.setIsDone(rs.getBoolean("isdone"));
			appointment.setIsPaid(rs.getBoolean("ispaid"));
			appointment.setRating(rs.getDouble("rating"));
			String master_email = rs.getString("master_email");
			String master_password = rs.getString("master_password");
			String master_name = rs.getString("master_name");
			String master_surname = rs.getString("master_surname");
			String master_tel = rs.getString("master_tel");
			double master_rating = rs.getDouble("master_rating");
			Role master_role = Role.HAIRDRESSER;
			String master_info = rs.getString("master_info");
			boolean master_isblocked = rs.getBoolean("master_isblocked");
			User master = new User(master_id, master_email, master_password, master_name, master_surname, master_tel,
					master_role, master_info, master_isblocked, master_rating);
			System.out.println("master is created");
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
			User user = new User(user_id, user_email, user_password, user_name, user_surname, user_tel, user_role,
					user_info, user_isblocked, 0);
			appointment.setUser(user);

			int service_id = rs.getInt("service_id");
			String service_name = rs.getString("service_name");
			String service_info = rs.getString("service_info");
			Service service = new Service(service_id, service_name, service_info);

			appointment.setService(service);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		}
		return appointment;
	}

	public List<Appointment> getMasterSchedule(Connection con, LocalDate date, User master) {
		logger.info("enter");
		
		List<Appointment> appointments = new ArrayList<>();
		PreparedStatement stmt = null;
		Statement stmt_create_table = null;
		Statement stmt_drop_table = null;
		ResultSet rs = null;
		try {
			stmt_drop_table = con.createStatement();
			int i = stmt_drop_table.executeUpdate(DROP_TABLE);
			System.out.println(i);
			stmt_create_table = con.createStatement();
			i = stmt_create_table.executeUpdate(CREATE_TABLE);
			System.out.println(i);

			stmt = con.prepareStatement(GET_MASTER_SCHEDULE);
			stmt.setDate(1, Date.valueOf(date));
			stmt.setInt(2, master.getId());
			stmt.setInt(3, master.getId());
			stmt.setDate(4, Date.valueOf(date));
			stmt.setInt(5, master.getId());

			rs = stmt.executeQuery();

			i = stmt_drop_table.executeUpdate(DROP_TABLE);
			System.out.println(i);

			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount();
			for (int k = 1; k <= columnCount; k++) {
				System.out.println(metadata.getColumnName(k) + ", ");

			}

			while (rs.next()) {
				System.out.println("adding...");
				appointments.add(exstractAppointment(rs));
			}
			return appointments;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
			DBConnection.closeStatement(stmt_drop_table);
			DBConnection.closeStatement(stmt_create_table);
		}
	}

	@Override
	public Appointment findById(Connection con, int id) {
		logger.info("enter");
		throw new UnsupportedOperationException();
	}

	public int getPrice(Connection con, User master, Service service) {
		logger.info("enter");
		
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
			}
			return price;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
		}
	}

	public void setIsPaid(Connection con, Appointment appointment, boolean isPaid) {
		logger.info("enter");
		
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(SET_ISPAID);
			stmt.setBoolean(1, isPaid);
			stmt.setInt(2, appointment.getMaster().getId());
			stmt.setDate(3, Date.valueOf(appointment.getDate()));
			stmt.setInt(4, appointment.getTimeslot());

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public void setIsDone(Connection con, Appointment appointment, boolean isDone) {
		logger.info("enter");
		
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(SET_ISDONE);
			stmt.setBoolean(1, isDone);
			stmt.setInt(2, appointment.getMaster().getId());
			stmt.setDate(3, Date.valueOf(appointment.getDate()));
			stmt.setInt(4, appointment.getTimeslot());

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public void setFeedback(Connection con, Appointment appointment, String feedback, double rating) {
		logger.info("enter");
		
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
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public void setMasterRating(Connection con, User master, double rating) {
		logger.info("enter");
		
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(SET_MASTER_RATING);

			stmt.setDouble(1, rating);
			stmt.setInt(2, master.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public double calculateMasterRating(Connection con, User master) {
		logger.info("enter");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		double rating = 0;
		try {
			stmt = con.prepareStatement(CALCULATE_MASTER_RATING);

			stmt.setInt(1, master.getId());

			rs = stmt.executeQuery();
			if (rs.next()) {
				rating = rs.getDouble("rating");
				System.out.println("average rating = " + rating);
			}
			return rating;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public List<Appointment> findByConditions(Connection con, LocalDate dateFrom, LocalDate dateTo, Integer master_id, Integer user_id,
			Integer service_id, Boolean isDone, Boolean isPaid, Boolean isRating) {
		logger.info("enter");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Appointment> appointments = new ArrayList<>();
		String sql = FIND_APPOINTMENTS_LIST;
		
		sql = addConditionsToSQL(sql);
		
		try {
			stmt = con.prepareStatement(sql);
			
			System.out.println(sql);

			stmt.setDate(1, Date.valueOf(dateFrom));
			stmt.setDate(2, Date.valueOf(dateTo));

			int i = 2;
			
			stmt.setInt(++i, (master_id == null) ? 0 : master_id);
			System.out.println(master_id == null);
			
			stmt.setBoolean(++i, (master_id == null));
			
			stmt.setInt(++i, (user_id == null) ? 0 : user_id);
			System.out.println(user_id == null);
			stmt.setBoolean(++i, (user_id == null));
			
			stmt.setInt(++i, (service_id == null) ? 0 : service_id);
			
			stmt.setBoolean(++i, (service_id == null));
			
			stmt.setBoolean(++i, (isDone == null) ? false : isDone);
			stmt.setBoolean(++i, (isDone == null));
			
			stmt.setBoolean(++i, (isPaid == null) ? false : isPaid);
			stmt.setBoolean(++i, (isPaid == null));
			
			stmt.setBoolean(++i, (isRating == null) ? true : !isRating);
			stmt.setBoolean(++i, (isRating == null) ? true : isRating);

			rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("adding...");
				appointments.add(exstractAppointment(rs));
			}
			return appointments;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);;
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	private String addConditionsToSQL(String sql) {
		logger.info("enter");
		
		StringBuilder str = new StringBuilder(sql);
		
		str.append(" and (master_id=? or ?)")
		.append(" and (user_id=? or ?)")
		.append(" and (service_id=? or ?)")
		.append(" and (isDone=? or ?)")
		.append(" and (isPaid=? or ?)")
		.append(" and (appointments.rating>0 or ?)")
		.append(" and (appointments.rating=0 or ?)")
		.append(" order by appointments.date, appointments.timeslot");
		return str.toString();
	}

	public List<Integer> getMasterFreeSlots(Connection con, LocalDate date, User master) {
		logger.info("enter");
		
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
				System.out.println("adding...");
				freeSlots.add(rs.getInt("timeslot"));
			}
			return freeSlots;

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(stmt);
			DBConnection.closeStatement(stmt_drop_table);
			DBConnection.closeStatement(stmt_create_table);
		}
	}

	public void setTime(Connection con, Appointment appointment, LocalDate newDate, Integer newTimeslot) {
		logger.info("enter");
		
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
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}

		
	}

}
