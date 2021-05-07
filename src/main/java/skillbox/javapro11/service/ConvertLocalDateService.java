package skillbox.javapro11.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ConvertLocalDateService {

    public static long convertLocalDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static long convertLocalDateToLong(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
