package controller.service.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.stream.Stream;

import org.junit.Test;

import service.utils.PasswordEncodingService;

public class PasswordEncodingServiceTest {

	@Test
	public void testPasswordEncodingService() {
		Stream.of("123456qaZ", "qhcnju765E", "095hnhgFTY", "hx))fj89Tgd").forEach(pass -> {
			try {
				String hashPass = PasswordEncodingService.generateStorngPasswordHash(pass);
				assertTrue(PasswordEncodingService.validatePassword(pass, hashPass));
				assertFalse(PasswordEncodingService.validatePassword(pass + pass, hashPass));
				assertFalse(PasswordEncodingService.validatePassword(pass + "0", hashPass));
				assertFalse(PasswordEncodingService.validatePassword("a" + pass, hashPass));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				fail();
			}
			try {
				String hashPass = PasswordEncodingService.generateStorngPasswordHash(pass);
				assertFalse(PasswordEncodingService.validatePassword(hashPass, pass));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				fail();
			} catch (NumberFormatException e) {
				assertNotNull(e);
			}
		});
	}

}
