package controller.service.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import controller.exceptions.IncorrectParamException;
import entity.Role;
import service.utils.ValidatorUtil;

public class ValidatorTest {
	@Test
	public void testNotValidName() {
		Stream.of(null, "", "aaw3edf", "56743", "8767h76", "_.98")
		.forEach(name -> assertFalse(ValidatorUtil.isValidName(name)));
	}
	@Test
	public void testValidName() {
		Stream.of("Helen", "anna", "Іван", "ванек")
		.forEach(name -> assertTrue(ValidatorUtil.isValidName(name)));
	}
	
	@Test
	public void testNotValidPassword() {
		Stream.of(null, "", "1234896", "ertg56", "апР67", "fgT56", "HGT67", "1qA7y")
		.forEach(pass -> assertFalse(ValidatorUtil.isValidPassword(pass)));
	}
	
	@Test
	public void testValidPassword() {
		Stream.of("12348erTY", "YGThj7863", "1111JIkL", "anna123SEM")
		.forEach(pass -> assertTrue(ValidatorUtil.isValidPassword(pass)));
	}
	@Test
	public void testNotValidEmail() {
		Stream.of(null, "", "mfjghv", "hyftg.hfy", "7665.gh", "@yujh.uf", "_ijuh@", "1256325")
		.forEach(email -> assertFalse(ValidatorUtil.isValidEmail(email)));
	}
	@Test
	public void testValidEmail() {
		Stream.of("tratata@ta.ty", "zhenya.smit@gmail.com", "client@client.ua", "AAA@yujh.uf", "Tatiana_Snegir@i.ua")
		.forEach(email -> assertTrue(ValidatorUtil.isValidEmail(email)));
	}
	
	@Test
	public void testNotValidTimeslot() {
		Stream.of(null, "", "tyjh", "mn", "mi8", "d5", "87", "36", "-7", "-14", "-38", "-248")
		.forEach(tm -> assertFalse(ValidatorUtil.isValidTimeslot(tm)));
	}
	
	@Test
	public void testValidTimeslot() {
		Stream.of("0", "1", "5", "11", "14", "23")
		.forEach(tm -> assertTrue(ValidatorUtil.isValidTimeslot(tm)));
	}
	
	@Test
	public void testNotValidInt() {
		Stream.of(null, "", "g78", "hjugf", "m", "-ok8", "null", "7y5", "-3", "-23", "-387", "3.5", "8,4", "67.9")
		.forEach(param -> assertFalse(ValidatorUtil.isValidInt(param)));
	}
	
	@Test
	public void testValidInt() {
		Stream.of("0", "1", "4", "38", "765", "9064", "123573")
		.forEach(param -> assertTrue(ValidatorUtil.isValidInt(param)));
	}
	@Test
	public void testNotValidRating() {
		Stream.of(null, "", "fgh", "tt", "m", "6", "11", "135", "-4", "-2u3", "5.4")
		.forEach(rat -> assertFalse(ValidatorUtil.isValidRating(rat)));
	}
	
	@Test
	public void testValidRating() {
		Stream.of("1", "2", "3", "4", "5", "0")
		.forEach(rat -> assertTrue(ValidatorUtil.isValidRating(rat)));
	}
	
	@Test
	public void testNotValidTel() {
		Stream.of(null, "", "458", "678541236", "+380254122336", "80684523696", "gjhh", "068521144", "099632kl78", " ", "04458796258")
		.forEach(tel -> assertFalse(ValidatorUtil.isValidTel(tel)));
	}
	
	@Test
	public void testValidTel() {
		Stream.of("0678544112", "0732658964", "0441111111", "0352658744", "0685552247")
		.forEach(tel -> assertTrue(ValidatorUtil.isValidTel(tel)));
	}
	
	@Test
	public void testNotValidSortorder() {
		Stream.of("as", "a", "3", "des", "down", "-09")
		.forEach(param -> assertFalse(ValidatorUtil.isValidSortOrder(param)));
	}
	
	@Test
	public void testValidSortorder() {
		Stream.of("asc", "desc")
		.forEach(param -> assertTrue(ValidatorUtil.isValidSortOrder(param)));
	}
	
	@Test
	public void testParseInt() {
		Stream.of(null, "", "tgfcv", "-89", "-5", "78.9")
		.forEach(param -> {
			try {
				assertEquals(0, ValidatorUtil.parseIntParameter(param));
				fail();
			} catch (IncorrectParamException e) {
				assertNotNull(e);
			}
		});
		
		IntStream.range(1, 150).forEach(i -> {
			try {
				assertEquals(i, ValidatorUtil.parseIntParameter(String.valueOf(i)));
			} catch (IncorrectParamException e) {
				fail();
			}
		});
	}
	
	@Test
	public void testParseBoolean() {
		Stream.of(null, "", "rhdh", "no", "fals", "ложь", "645").forEach(param -> {
			try {
				assertEquals(false, ValidatorUtil.parseBooleanParameter(param));
				fail();
			} catch (IncorrectParamException e) {
				assertNotNull(e);
			}
		});
		try {
			assertEquals(false, ValidatorUtil.parseBooleanParameter("false"));
		} catch (IncorrectParamException e) {
			fail();
		}
		try {
			assertEquals(true, ValidatorUtil.parseBooleanParameter("true"));
		} catch (IncorrectParamException e) {
			fail();
		}
	}
	@Test
	public void testParseTimeslot() {
		Stream.of(null, "", "25", "789", "-9", "222", "fghf").forEach(param -> {
			try {
				assertEquals(0, ValidatorUtil.parseTimeslotParameter(param));
				fail();
			} catch (IncorrectParamException e) {
				assertNotNull(e);
			}
		});
		IntStream.range(0, 23).forEach(i -> {
			try {
				assertEquals(i, ValidatorUtil.parseTimeslotParameter(String.valueOf(i)));
			} catch (IncorrectParamException e) {
				fail();
			}
		});
	}
	
	@Test
	public void testParseRating() {
		Stream.of(null, "", "a", "22", "34", "7", "45gf").forEach(param -> {
			try {
				assertEquals(0, ValidatorUtil.parseRatingParameter(param));
				fail();
			} catch (IncorrectParamException e) {
				assertNotNull(e);
			}
		});
		IntStream.range(0, 5).forEach(i -> {
			try {
				assertEquals(i, ValidatorUtil.parseRatingParameter(String.valueOf(i)));
			} catch (IncorrectParamException e) {
				fail();
			}
		});
	}
	
	@Test
	public void testParseDate() {
		Stream.of(null, "", "a", "2022-02-31", "2019-04-70", "234-78-09", "2021-13-08").forEach(param -> {
			try {
				assertEquals(LocalDate.now(), ValidatorUtil.parseDateParameter(param));
				fail();
			} catch (IncorrectParamException e) {
				assertNotNull(e);
			}
		});
		Stream.of("2022-03-14", "2022-02-24", "2021-09-22", "1838-10-30").forEach(param -> {
			try {
				assertEquals(LocalDate.parse(param), ValidatorUtil.parseDateParameter(param));
			} catch (IncorrectParamException e) {
				fail();
			}
		});
	}
	
	@Test
	public void testParseRole() {
		Stream.of(null, "", "a", "sdff", "GUESST", "7", "45gf").forEach(param -> {
			try {
				assertEquals(Role.ADMIN, ValidatorUtil.parseRoleParameter(param));
				fail();
			} catch (IncorrectParamException e) {
				assertNotNull(e);
			}
		});
		
		for (Role role : Role.values()) {
			try {
				assertEquals(role, ValidatorUtil.parseRoleParameter(role.toString()));
			} catch (IncorrectParamException e) {
				fail();
			}
			try {
				assertEquals(role, ValidatorUtil.parseRoleParameter(role.toString().toLowerCase()));
			} catch (IncorrectParamException e) {
				fail();
			}
		}
			
		
	}
}
