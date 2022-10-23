package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UserTest {
	@Test
	public void testCreateUser() {
		User user = new User();
		assertNull(user.getName());
		
		user = new User(0, "vvv@vv.ua", "123", "Vanya", "Dyrkin", "0632365254", Role.CLIENT, "", false, 0, "en");
		assertEquals(user.getId(), 0);
		
		user.setId(5);
		user.setEmail("aaa@vv.ua");
		user.setName("Lenochka");
		user.setSurname("Andreeva");
		user.setPassword("321");
		user.setTel("0685633322");
		user.setRole(Role.HAIRDRESSER);
		user.setInfo("Best");
		user.setBlocked(false);
		user.setRating(2.67);
		user.setCurrentLang("uk");
		
		assertEquals(user.getEmail(), "aaa@vv.ua");
		assertEquals(user.getPassword(), "321");
		assertEquals(user.getName(), "Lenochka");
		assertEquals(user.getSurname(), "Andreeva");
		assertEquals(user.getTel(), "0685633322");
		assertEquals(user.getRole(), Role.HAIRDRESSER);
		assertEquals(user.getInfo(), "Best");
		assertEquals(user.getIsBlocked(), false);
		assertEquals(user.getCurrentLang(), "uk");
		assertEquals(String.valueOf(user.getRating()),"2.67");		
		
		assertEquals(user.toString(), "User [email=aaa@vv.ua, name=Lenochka, surname=Andreeva, role=HAIRDRESSER ]");
		
		User user2 = new User();
		assertNotEquals(user, user2);
		user2.setId(5);
		assertEquals(user, user2);
		
	}
}
