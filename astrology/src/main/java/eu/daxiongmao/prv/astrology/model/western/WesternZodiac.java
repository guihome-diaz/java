package eu.daxiongmao.prv.astrology.model.western;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * List of western zodiac signs.
 *
 * @author Guillaume Diaz
 * @version 1.0 - 2016/12
 */
public enum WesternZodiac {

    AQUARIUS(Calendar.JANUARY, 20, Calendar.FEBRUARY, 18), 
    PISCES(Calendar.FEBRUARY, 19, Calendar.MARCH, 20), 
    ARIES(Calendar.MARCH, 21, Calendar.APRIL, 19), 
    TAURUS(Calendar.APRIL, 20, Calendar.MAY, 20), 
    GEMINI(Calendar.MAY, 21, Calendar.JUNE, 20), 
    CANCER(Calendar.JUNE, 21, Calendar.JULY, 22), 
    LEO(Calendar.JULY, 23, Calendar.AUGUST, 22), 
    VIRGO(Calendar.AUGUST, 23, Calendar.SEPTEMBER, 22), 
    LIBRA(Calendar.SEPTEMBER, 23, Calendar.OCTOBER, 22), 
    SCORPIO(Calendar.OCTOBER, 23, Calendar.NOVEMBER, 21), 
    SAGITTARUS(Calendar.NOVEMBER, 22, Calendar.DECEMBER, 21), 
    CAPRICORN(Calendar.DECEMBER, 22, Calendar.JANUARY, 19);

    private int startMonth;
    private int startDay;
    private int endMonth;
    private int endDay;

    WesternZodiac(final int startMonth, final int startDay, final int endMonth, final int endDay) {
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endMonth = endMonth;
        this.endDay = endDay;
    }

    /**
     * @return start month (java.util.Calendar class value)
     */
    public int getStartMonth() {
        return startMonth;
    }

    /**
     * @return start day (inclusive == this is part of the sign, starting 00:00:00)
     */
    public int getStartDay() {
        return startDay;
    }

    /**
     * @return end month (java.util.Calendar class value)
     */
    public int getEndMonth() {
        return endMonth;
    }

    /**
     * @return end day (inclusive == this is part of the sign, ending 23:59:59)
     */
    public int getEndDay() {
        return endDay;
    }

    /** Starting date of the sign for a particular year. */
    public LocalDate getStartDate(final int year) {
        if (WesternZodiac.CAPRICORN.equals(this)) {
            // CAPRICORN starts in previous year (only CAPRICORN)
            return LocalDate.now().withYear(year - 1).withMonth(endMonth + 1).withDayOfMonth(endDay);
        } else {
            return LocalDate.now().withYear(year).withMonth(startMonth + 1).withDayOfMonth(startDay);
        }
    }

    /** Ending date of the sign for a particular year. */
    public LocalDate getEndDate(final int year) {
        return LocalDate.now().withYear(year).withMonth(endMonth + 1).withDayOfMonth(endDay);
    }
}
