package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.exceptions.CreatingAppointmentException;
import controller.exceptions.DeletingAppointmentException;
import controller.exceptions.FindingAppointmentException;
import controller.exceptions.UpdatingAppointmentException;
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

	public Appointment findAppointmentByKey(int master_id, LocalDate date, int timeslot) throws FindingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		Appointment appointment = null;
		try {
			con = dao.getConnection();
			appointment = dao.findByKey(con, master_id, date, timeslot);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingAppointmentException("Cannot find appointment by key: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return appointment;
	}

	public List<Appointment> findAllAppointments() throws FindingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		List<Appointment> services = new ArrayList<>();
		try {
			con = dao.getConnection();
			services = dao.findAll(con);

		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingAppointmentException("Cannot find all appointments: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return services;
	}

	public List<Appointment> getMasterSchedule(LocalDate date, User master) throws FindingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		List<Appointment> appointments = new ArrayList<>();
		try {
			con = dao.getConnection();
			appointments = dao.getMasterSchedule(con, date, master);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingAppointmentException("Cannot get masters schedule: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}

		return appointments;
	}

	public Appointment createAppointment(Appointment appointment) throws CreatingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			appointment = dao.save(con, appointment);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new CreatingAppointmentException("Cannot create appointment: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return appointment;
	}

	public int getPriceByMasterAndService(User master, Service service) throws FindingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		int price = 0;
		try {
			con = dao.getConnection();
			price = dao.getPrice(con, master, service);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingAppointmentException("Cannot get price: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return price;
	}

	public void deleteAppointment(Appointment appointment) throws DeletingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.delete(con, appointment);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new DeletingAppointmentException("Cannot delete appointment: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}

	public void setPayAppointment(Appointment appointment, boolean isPaid) throws UpdatingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.setIsPaid(con, appointment, isPaid);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingAppointmentException("Cannot set payment for appointment: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}

	public void setDoneAppointment(Appointment appointment, boolean isDone) throws UpdatingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.setIsDone(con, appointment, isDone);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingAppointmentException("Cannot set done for appointment: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}

	public void setFeedbackForAppointmentandUpdateMaster(Appointment appointment, double rating, String feedback)
			throws UpdatingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			con.setAutoCommit(false);
			dao.setFeedback(con, appointment, feedback, rating);
			double newMasterRating = dao.calculateMasterRating(con, appointment.getMaster());
			dao.setMasterRating(con, appointment.getMaster(), newMasterRating);
			con.commit();
			logger.trace("transaction is commited");
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e, "transaction is cancelled");
			try {
				con.rollback();
			} catch (SQLException e1) {
				logger.error(e.getMessage(), e1, "transaction cancelling failed");
				throw new UpdatingAppointmentException("Cannot set feedback for appointment and update master: " + e.getMessage());
			}
			throw new UpdatingAppointmentException("Cannot set feedback for appointment and update master: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}

	public List<Appointment> findAppointmentsByConditions(LocalDate dateFrom, LocalDate dateTo, Integer master_id,
			Integer user_id, Integer service_id, Boolean isDone, Boolean isPaid, Boolean isRating) throws FindingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		List<Appointment> appointments = new ArrayList<>();

		try {
			con = dao.getConnection();
			appointments = dao.findByConditions(con, dateFrom, dateTo, master_id, user_id, service_id, isDone, isPaid,
					isRating);
			
			return appointments;
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingAppointmentException("Cannot find all appointments by conditions: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}

	public List<Integer> getMasterFreeSlots(LocalDate date, User master) throws FindingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		List<Integer> freeSlots = new ArrayList<>();
		try {
			con = dao.getConnection();
			freeSlots = dao.getMasterFreeSlots(con, date, master);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new FindingAppointmentException("Cannot find masterfree slots: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
		return freeSlots;
	}

	public void setTimeAppointment(Appointment appointment, LocalDate newDate, Integer newTimeslot) throws UpdatingAppointmentException {
		logger.trace("enter");

		Connection con = null;
		try {
			con = dao.getConnection();
			dao.setTime(con, appointment, newDate, newTimeslot);
		} catch (SQLException |ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UpdatingAppointmentException("Cannot set time for appointment: " + e.getMessage());
		} finally {
			DBConnection.close(con);
		}
	}
}