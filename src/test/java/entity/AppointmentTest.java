package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

public class AppointmentTest {
	@Test
	public void testCreateAppointment() {
		Appointment appointment = new Appointment();
		assertNull(appointment.getDate());
		User user = new User();
		user.setId(1);
		User master = new User();
		master.setId(2);
		Service service = new Service(0, "serv", "lala");
		appointment = new Appointment(LocalDate.now(), 10, null, null, null, 200, false, false, "", 0);
		assertEquals(appointment.getDate(), LocalDate.now());
		appointment.setDate(LocalDate.parse("2022-09-22"));
		appointment.setTimeslot(14);
		appointment.setUser(user);
		appointment.setMaster(master);
		appointment.setService(service);
		appointment.setSum(350);
		appointment.setIsDone(true);
		appointment.setIsPaid(false);
		appointment.setFeedback("Perfect!");
		appointment.setRating(5);
		assertEquals(appointment.getDate(), LocalDate.parse("2022-09-22"));
		assertEquals(appointment.getTimeslot(), 14);
		assertEquals(appointment.getUser().getId(), 1);
		assertEquals(appointment.getMaster().getId(), 2);
		assertEquals(appointment.getService().getName(), "serv");
		assertEquals(appointment.getSum(), 350);
		assertEquals(appointment.getIsDone(), true);
		assertEquals(appointment.getIsPaid(), false);
		assertEquals(appointment.getFeedback(), "Perfect!");
		assertEquals(String.valueOf(appointment.getRating()), "5.0");
		assertEquals(appointment.toString(),
				"Appointment [date=2022-09-22, timeslot=14, master=User [email=null, name=null, surname=null, role=null ], user=User [email=null, name=null, surname=null, role=null ], service=Service [name=serv], sum=350, feedback=Perfect!]");
		Appointment appointment2 = new Appointment();
		assertNotEquals(appointment, appointment2);
		appointment2.setDate(appointment.getDate());
		appointment2.setMaster(appointment.getMaster());
		appointment2.setTimeslot(appointment.getTimeslot());
		assertEquals(appointment, appointment2);
	}
}
