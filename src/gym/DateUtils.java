package gym;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static LocalDate parseDate(String date) throws DateTimeParseException {
        return LocalDate.parse(date, DATE_FORMAT);
    }
    public static boolean isOverAge(String dateOfBirth, int age) throws DateTimeParseException {
        LocalDate birthDate = parseDate(dateOfBirth);
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        return period.getYears() >= age;
    }
    public static int calculateAge(String dateOfBirth) throws DateTimeParseException {
        LocalDate birthDate = parseDate(dateOfBirth);
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }
}
