package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ServiceTest {
	@Test
	public void testCreateService() {
		Service service = new Service();
		assertNull(service.getName());
		service = new Service(0, "Haircut", "tralala la la");
		assertEquals(service.getName(), "Haircut");
		assertEquals(service.getInfo(), "tralala la la");
		assertEquals(service.getId(), 0);
		service.setId(2);
		service.setName("Cut hair");
		service.setInfo("lalala");
		Service service2 = new Service();
		assertNotEquals(service, service2);
		service2.setId(2);
		assertEquals(service, service2);
		assertEquals(service.toString(), "Service [name=Cut hair]");
	}
}
