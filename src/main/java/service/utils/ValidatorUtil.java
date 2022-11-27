package service.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import controller.exceptions.IncorrectParamException;
import entity.Role;

/**
 * Class for validating data, received from Front-end
 * 
 * @author yevgenia.kovalova
 *
 */

public class ValidatorUtil {
    private static final String NAME_REGEX = "^[\\D ,.'-]+$";
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$";
	private static final String TIMESLOT_REGEX = "2[0-3]|[0-1]?[0-9]";
	private static final String ID_REGEX = "[0-9]+";
	private static final String TEL_REGEX = "0\\d{9}";
	private static final String RATING_REGEX = "[0-5]";

	private ValidatorUtil() {}

    public static boolean isValidName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean isValidTimeslot(String timeslot) {
        return timeslot != null && timeslot.matches(TIMESLOT_REGEX);
    }

    public static boolean isValidInt(String intParam) {
        return intParam != null && intParam.matches(ID_REGEX);
    }

    public static boolean isValidRating(String rating) {
        return rating != null && rating.matches(RATING_REGEX);
    }
    
    public static boolean isValidText(String text) {
        return text != null && !"".equals(text);
    }
    
    public static boolean isValidTel(String tel) {
    	return tel != null && tel.matches(TEL_REGEX);
    }
    
    public static boolean isValidBoolean(String booleanParam) {
        return "true".equals(booleanParam) || "false".equals(booleanParam);
    }
    
    public static boolean isValidSortOrder(String sortOrder) {
        return "asc".equals(sortOrder) || "desc".equals(sortOrder);
    }
    
    public static int parseIntParameter(String intParam) throws IncorrectParamException {
        if(isValidInt(intParam)) {
            return Integer.parseInt(intParam);
        }
        throw new IncorrectParamException("unable to parse int parameter");
    }

    public static int parseRatingParameter(String ratingParam) throws IncorrectParamException {
        if(isValidRating(ratingParam)) {
            return Integer.parseInt(ratingParam);
        }
        throw new IncorrectParamException("unable to parse rating parameter");
    }

    public static int parseTimeslotParameter(String timeslotParam) throws IncorrectParamException {
        if(isValidTimeslot(timeslotParam)) {
            return Integer.parseInt(timeslotParam);
        }
        throw new IncorrectParamException("unable to parse timeslot");
    }
    public static LocalDate parseDateParameter(String dateParam) throws IncorrectParamException {
            try {
                return LocalDate.parse(dateParam);
            } catch(DateTimeParseException | NullPointerException e) {
                throw new IncorrectParamException("unable to parse date");
            }
        
    }
    public static boolean parseBooleanParameter(String booleanParam) throws IncorrectParamException {
        if(isValidBoolean(booleanParam)) {
            return Boolean.parseBoolean(booleanParam);
        }
        throw new IncorrectParamException("unable to parse boolean parameter");
    }

	public static Role parseRoleParameter(String role) throws IncorrectParamException {
		try {
            return Role.valueOf(role.toUpperCase());
        } catch(IllegalArgumentException | NullPointerException e) {
            throw new IncorrectParamException("unable to parse role");
        }
	}

}
