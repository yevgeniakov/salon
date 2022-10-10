package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DBConnection;
import dao.impl.AppointmentDao;
import entity.Appointment;
import entity.Service;
import entity.User;

public class AppointmentManager {
	private static AppointmentManager instance;
	private AppointmentDao dao;
	private static final Logger logger = LogManager.getLogger(AppointmentManager.class);

	public static synchronized AppointmentManager getInstance() {
		if (instance == null) {
			instance = new AppointmentManager();
		}
		return instance;
	}

	private AppointmentManager() {
		this.dao = AppointmentDao.getInstance();
	}

	public Appointment findAppointmentByKey(int master_id, LocalDate date, int timeslot) throws Exception {
		logger.info("enter");

		Connection con = null;
		Appointment appointment = null;
		try {
			con = dao.getConnection();
			appointment = dao.findByKey(con, master_id, date, timeslot);
			System.out.println("app in manager: " + appointment);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO throw new custom Exception
		} finally {
			DBConnection.close(con);
		}
		return appointment;
	}

	public List<Appointment> findAllAppointments() throws Exception {
		logger.info("enter");

		Connection con = null;
		List<Appointment> services = new ArrayList<>();
		try {
			con = dao.getConnection();
			services = dao.findAll(con);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO throw new custom Exception
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

	public List<Appointment> getMasterSchedule(LocalDate date, User master) throws Exception {
		logger.info("enter");

		System.out.println("#Manager#getMasterSchedule");
		Connection con = null;
		List<Appointment> appointments = new ArrayList<>();
		try {
			con = dao.getConnection();
			appointments = dao.getMasterSchedule(con, date, master);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO throw new custom Exception
		} finally {
			DBConnection.close(con);
		}
		System.out.println(appointments);
		return appointments;
	}

	public Appointment createAppointment(Appointment appointment) throws Exception {
		logger.info("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			appointment = dao.save(con, appointment);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

			// TODO (2) throw your own exception
			throw new Exception("Cannot create service", e);
		} finally {
			DBConnection.close(con);
		}
		return appointment;
	}

	public int getPriceByMasterAndService(User master, Service service) throws Exception {
		logger.info("enter");

		Connection con = null;
		int price = 0;
		try {
			con = dao.getConnection();
			price = dao.getPrice(con, master, service);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

			// TODO (2) throw your own exception
			throw new Exception("Cannot find price", e);
		} finally {
			DBConnection.close(con);
		}
		return price;
	}

	public void deleteAppointment(Appointment appointment) throws Exception {
		logger.info("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.delete(con, appointment);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

			// TODO (2) throw your own exception
			throw new Exception("Cannot delete appointment", e);
		} finally {
			DBConnection.close(con);
		}
	}

	public void setPayAppointment(Appointment appointment, boolean isPaid) throws Exception {
		logger.info("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.setIsPaid(con, appointment, isPaid);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

			// TODO (2) throw your own exception
			throw new Exception("Cannot set pay for appointment", e);
		} finally {
			DBConnection.close(con);
		}
	}

	public void setDoneAppointment(Appointment appointment, boolean isDone) throws Exception {
		logger.info("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.setIsDone(con, appointment, isDone);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			// TODO (2) throw your own exception
			throw new Exception("Cannot set done for appointment", e);
		} finally {
			DBConnection.close(con);
		}
	}

	public void setFeedbackForAppointmentandUpdateMaster(Appointment appointment, double rating, String feedback)
			throws Exception {
		logger.info("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			con.setAutoCommit(false);
			dao.setFeedback(con, appointment, feedback, rating);
			double newMasterRating = dao.calculateMasterRating(con, appointment.getMaster());
			dao.setMasterRating(con, appointment.getMaster(), newMasterRating);
			con.commit();
			logger.trace("transaction is commited");
		} catch (SQLException e) {
			logger.error(e.getMessage(), e, "transaction is cancelled");
			con.rollback();
			// TODO (2) throw your own exception
			throw new Exception("Cannot set feedback for appointment", e);
		} finally {
			DBConnection.close(con);
		}
	}

	public List<Appointment> findAppointmentsByConditions(LocalDate dateFrom, LocalDate dateTo, Integer master_id,
			Integer user_id, Integer service_id, Boolean isDone, Boolean isPaid, Boolean isRating) throws Exception {
		logger.info("enter");

		Connection con = null;
		List<Appointment> appointments = new ArrayList<>();

		try {
			con = dao.getConnection();
			System.out.println("con obtain");
			appointments = dao.findByConditions(con, dateFrom, dateTo, master_id, user_id, service_id, isDone, isPaid,
					isRating);
			System.out.println("service: app size is " + appointments.size());
			return appointments;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

			// TODO (2) throw your own exception
			throw new Exception("Cannot get list of appointments", e);
		} finally {
			DBConnection.close(con);
		}
	}

	public List<Integer> getMasterFreeSlots(LocalDate date, User master) throws Exception {
		logger.info("enter");

		Connection con = null;
		List<Integer> freeSlots = new ArrayList<>();
		try {
			con = dao.getConnection();
			freeSlots = dao.getMasterFreeSlots(con, date, master);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

			// TODO (2) throw your own exception
			throw new Exception("Cannot find free slots", e);
		} finally {
			DBConnection.close(con);
		}
		return freeSlots;
	}

	public void setTimeAppointment(Appointment appointment, LocalDate newDate, Integer newTimeslot) throws Exception {
		logger.info("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.setTime(con, appointment, newDate, newTimeslot);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

			// TODO (2) throw your own exception
			throw new Exception("Cannot set time for appointment", e);
		} finally {
			DBConnection.close(con);
		}
	}
}