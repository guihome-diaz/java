package eu.daxiongmao.prv.server.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    /** Timezone to use. */
    private static final ZoneId TIMEZONE = ZoneId.systemDefault();

    public static Date asDate(final LocalDate localDate) {
        // LocalDate always start at 00:00:00
        return Date.from(localDate.atStartOfDay().atZone(TIMEZONE).toInstant());
    }

    public static Date asDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(TIMEZONE).toInstant());
    }

    public static LocalDate asLocalDate(final Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(TIMEZONE).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(final Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(TIMEZONE).toLocalDateTime();
    }
}
