package gym.management;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DateTimeFormatter CUSTOM_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    public static LocalDate parseDate(String date) throws DateTimeException {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime.contains("T")) {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            return LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
        }
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(CUSTOM_DATETIME_FORMATTER);
    }


    public static boolean isOverAge(String birthDate, int age) {
        LocalDate date = parseDate(birthDate);
        return date.plusYears(age).isBefore(LocalDate.now()) || date.plusYears(age).isEqual(LocalDate.now());
    }

    public static boolean dateNotPassed(String date) {
        return LocalDate.parse(date, DATE_FORMATTER).isAfter(LocalDate.now());
    }

    public static boolean dateTimeNotPassed(String dateTime) {
        return parseDateTime(dateTime).isAfter(LocalDateTime.now());
    }
}