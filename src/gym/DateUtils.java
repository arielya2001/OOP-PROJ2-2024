package gym;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    public static LocalDate parseDate(String date) throws DateTimeException {
        if (date.length()>10)
        {
            date=date.substring(0,10);
        }
        return LocalDate.parse(date, FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }


    public static boolean isOverAge(String birthDate, int age) {
        LocalDate date = parseDate(birthDate);
        return date.plusYears(age).isBefore(LocalDate.now()) || date.plusYears(age).isEqual(LocalDate.now());
    }

    public static boolean dateNotPassed(String date) {
        return parseDate(date).isAfter(LocalDate.now());
    }
}
