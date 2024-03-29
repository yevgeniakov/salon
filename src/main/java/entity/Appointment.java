package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Appointment {
	private LocalDate date;
	private int timeslot;
	private User master;
	private User user;
	private Service service;
	private int sum;
	private boolean isDone;
	private boolean isPaid;
	private String feedback;
	private double rating;

	public Appointment(LocalDate date, int timeslot, User master, User user, Service service, int sum, boolean isDone,
			boolean isPaid, String feedback, double rating) {

		this.date = date;
		this.timeslot = timeslot;
		this.master = master;
		this.user = user;
		this.service = service;
		this.sum = sum;
		this.isDone = isDone;
		this.isPaid = isPaid;
		this.feedback = feedback;
		this.rating = rating;
	}

	public Appointment() {

	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(int timeslot) {
		this.timeslot = timeslot;
	}

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

	public boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Appointment [date=" + date + ", timeslot=" + timeslot + ", master=" + master + ", user=" + user
				+ ", service=" + service + ", sum=" + sum + ", feedback=" + feedback + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, master, timeslot);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(date, other.date) && Objects.equals(master, other.master) && timeslot == other.timeslot;
	}

}
