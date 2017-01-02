package eu.daxiongmao.prv.astrology.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.daxiongmao.prv.astrology.model.chinese.ChineseElement;
import eu.daxiongmao.prv.astrology.model.chinese.ChineseYear;
import eu.daxiongmao.prv.astrology.model.chinese.ChineseZodiac;

public class ChineseZodiacService {

    private static final List<ChineseYear> YEARS = new ArrayList<>();

    /** Utility class should not have public constructor # private factory design pattern. */
    private ChineseZodiacService() {
    }

    /**
     * @return all years from 1900 until 2043 with their corresponding Chinese zodiac. <br>
     *         The years are sorted by start date (from 1900 -> to -> 2043)
     */
    public static List<ChineseYear> getChineseYears() {
        if (YEARS.isEmpty()) {
            YEARS.addAll(getRatYears());
            YEARS.addAll(getOxYears());
            YEARS.addAll(getTigerYears());
            YEARS.addAll(getRabbitYears());
            YEARS.addAll(getDragonYears());
            YEARS.addAll(getSnakeYears());
            YEARS.addAll(getHorseYears());
            YEARS.addAll(getGoatYears());
            YEARS.addAll(getMonkeyYears());
            YEARS.addAll(getRoosterYears());
            YEARS.addAll(getDogYears());
            YEARS.addAll(getPigYears());
            // sort the list
            Collections.sort(YEARS);
        }
        return YEARS;
    }

    /**
     * To get the Chinese year that match the given Gregorian birth date.
     *
     * @param birthDate
     *            birth date (Gregorian == common area)
     * @return the corresponding Chinese year
     */
    public static ChineseYear getChineseBirthYear(final LocalDate birthDate) {
        getChineseYears();
        ChineseYear birthYear = null;
        for (final ChineseYear year : YEARS) {
            if ((birthDate.isEqual(year.getStartDate()) || birthDate.isAfter(year.getStartDate()))
                    && (birthDate.isEqual(year.getEndDate()) || birthDate.isBefore(year.getEndDate()))) {
                birthYear = year;
                break;
            }
        }
        return birthYear;
    }

    /**
     * To get the Chinese year that match the given year and expected sign.
     *
     * @param year
     *            expected year
     * @param expectedSign
     *            expected (wanted) Chinese sign
     * @return the corresponding Chinese year |OR| null
     */
    public static ChineseYear getChineseYearBySign(final int year, final ChineseZodiac expectedSign) {
        getChineseYears();
        ChineseYear result = null;
        for (final ChineseYear chineseYear : YEARS) {
            if (chineseYear.getStartDate().getYear() == year || chineseYear.getEndDate().getYear() == year) {
                if (chineseYear.getZodiac().name().equals(expectedSign.name())) {
                    result = chineseYear;
                    break;
                }
            }
        }
        return result;
    }

    private static void createAndAddNewChineseYear(final List<ChineseYear> years, final ChineseZodiac zodiac, final ChineseElement element, final int startYear,
            final int startMonth, final int startDayOfMonth, final int endYear, final int endMonth, final int endDayOfMonth) {
        years.add(createNewChineseYear(zodiac, element, startYear, startMonth, startDayOfMonth, endYear, endMonth, endDayOfMonth));
    }

    private static ChineseYear createNewChineseYear(final ChineseZodiac zodiac, final ChineseElement element, final int startYear, final int startMonth,
            final int startDayOfMonth, final int endYear, final int endMonth, final int endDayOfMonth) {
        final LocalDate startDate = LocalDate.now().withYear(startYear).withMonth(startMonth).withDayOfMonth(startDayOfMonth);
        final LocalDate endDate = LocalDate.now().withYear(endYear).withMonth(endMonth).withDayOfMonth(endDayOfMonth);
        return new ChineseYear(zodiac, element, startDate, endDate);
    }

    public static List<ChineseYear> getRatYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.METAL, 1900, 1, 31, 1901, 2, 18);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.WATER, 1912, 2, 18, 1913, 2, 5);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.WOOD, 1924, 2, 5, 1925, 1, 24);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.FIRE, 1936, 1, 24, 1937, 2, 10);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.EARTH, 1948, 2, 10, 1949, 1, 28);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.METAL, 1960, 1, 28, 1961, 2, 14);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.WATER, 1972, 2, 15, 1973, 2, 2);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.WOOD, 1984, 2, 2, 1985, 2, 19);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.FIRE, 1996, 2, 19, 1997, 2, 6);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.EARTH, 2008, 2, 7, 2009, 1, 25);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.METAL, 2020, 1, 25, 2021, 2, 11);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RAT, ChineseElement.WATER, 2032, 2, 11, 2033, 1, 30);
        return signYears;
    }

    public static List<ChineseYear> getOxYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.METAL, 1901, 2, 19, 1902, 2, 7);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.WATER, 1913, 2, 6, 1914, 1, 25);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.WOOD, 1925, 1, 25, 1926, 2, 12);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.FIRE, 1937, 2, 11, 1938, 1, 30);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.EARTH, 1949, 1, 29, 1950, 2, 16);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.METAL, 1961, 2, 15, 1962, 2, 4);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.WATER, 1973, 2, 3, 1974, 1, 22);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.WOOD, 1985, 2, 20, 1986, 2, 8);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.FIRE, 1997, 2, 7, 1998, 1, 27);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.EARTH, 2009, 1, 26, 2010, 2, 13);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.METAL, 2021, 2, 12, 2022, 1, 31);
        createAndAddNewChineseYear(signYears, ChineseZodiac.OX, ChineseElement.WATER, 2033, 1, 31, 2034, 2, 18);
        return signYears;
    }

    public static List<ChineseYear> getTigerYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.WATER, 1902, 2, 8, 1903, 1, 28);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.WOOD, 1914, 1, 26, 1915, 2, 13);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.FIRE, 1926, 2, 13, 1927, 2, 1);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.EARTH, 1938, 1, 31, 1939, 2, 18);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.METAL, 1950, 2, 17, 1951, 2, 5);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.WATER, 1962, 2, 5, 1963, 1, 24);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.WOOD, 1974, 1, 23, 1975, 2, 10);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.FIRE, 1986, 2, 9, 1987, 1, 28);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.EARTH, 1998, 1, 28, 1999, 2, 15);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.METAL, 2010, 2, 14, 2011, 2, 2);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.WATER, 2022, 2, 1, 2023, 1, 21);
        createAndAddNewChineseYear(signYears, ChineseZodiac.TIGER, ChineseElement.WOOD, 2034, 2, 19, 2035, 2, 7);
        return signYears;
    }

    public static List<ChineseYear> getRabbitYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.WATER, 1903, 1, 29, 1904, 2, 15);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.WOOD, 1915, 2, 14, 1916, 2, 2);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.FIRE, 1927, 2, 2, 1928, 1, 22);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.EARTH, 1939, 2, 19, 1940, 2, 7);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.METAL, 1951, 2, 6, 1952, 1, 26);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.WATER, 1963, 1, 25, 1964, 2, 12);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.WOOD, 1975, 2, 11, 1976, 1, 30);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.FIRE, 1987, 1, 29, 1988, 2, 5);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.EARTH, 1999, 2, 16, 2000, 2, 4);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.METAL, 2011, 2, 3, 2012, 1, 22);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.WATER, 2023, 1, 2, 2024, 2, 9);
        createAndAddNewChineseYear(signYears, ChineseZodiac.RABBIT, ChineseElement.WOOD, 2035, 2, 8, 2036, 1, 27);
        return signYears;
    }

    public static List<ChineseYear> getDragonYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.WOOD, 1904, 2, 16, 1905, 2, 3);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.FIRE, 1916, 2, 3, 1917, 1, 22);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.EARTH, 1928, 1, 23, 1929, 2, 9);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.METAL, 1940, 2, 8, 1941, 1, 26);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.WATER, 1952, 1, 27, 1953, 2, 13);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.WOOD, 1964, 2, 13, 1965, 2, 1);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.FIRE, 1976, 1, 31, 1977, 2, 17);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.EARTH, 1988, 2, 6, 1989, 2, 5);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.METAL, 2000, 2, 5, 2001, 1, 23);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.WATER, 2012, 1, 23, 2013, 2, 9);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.WOOD, 2024, 2, 10, 2025, 1, 28);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DRAGON, ChineseElement.FIRE, 2036, 1, 28, 2037, 2, 14);
        return signYears;
    }

    public static List<ChineseYear> getSnakeYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.WOOD, 1905, 2, 4, 1906, 1, 24);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.FIRE, 1917, 1, 23, 1918, 2, 10);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.EARTH, 1929, 2, 10, 1930, 1, 29);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.METAL, 1941, 1, 27, 1942, 2, 14);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.WATER, 1953, 2, 14, 1954, 2, 2);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.WOOD, 1965, 2, 2, 1966, 1, 20);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.FIRE, 1977, 2, 18, 1978, 2, 6);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.EARTH, 1989, 2, 6, 1990, 1, 26);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.METAL, 2001, 1, 24, 2002, 2, 11);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.WATER, 2013, 2, 10, 2014, 1, 30);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.WOOD, 2025, 1, 29, 2026, 2, 16);
        createAndAddNewChineseYear(signYears, ChineseZodiac.SNAKE, ChineseElement.FIRE, 2037, 2, 15, 2038, 2, 3);
        return signYears;
    }

    public static List<ChineseYear> getHorseYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.FIRE, 1906, 1, 25, 1907, 2, 12);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.EARTH, 1918, 2, 11, 1919, 1, 31);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.METAL, 1930, 1, 30, 1931, 2, 16);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.WATER, 1942, 2, 15, 1943, 2, 4);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.WOOD, 1954, 2, 3, 1955, 1, 23);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.FIRE, 1966, 1, 21, 1967, 2, 8);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.EARTH, 1978, 2, 7, 1979, 1, 27);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.METAL, 1990, 1, 27, 1991, 2, 14);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.WATER, 2002, 2, 12, 2003, 1, 31);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.WOOD, 2014, 1, 31, 2015, 2, 18);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.FIRE, 2026, 2, 17, 2027, 2, 5);
        createAndAddNewChineseYear(signYears, ChineseZodiac.HORSE, ChineseElement.EARTH, 2038, 2, 4, 2039, 1, 23);
        return signYears;
    }

    public static List<ChineseYear> getGoatYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.FIRE, 1907, 2, 13, 1908, 2, 1);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.EARTH, 1919, 2, 1, 1920, 2, 19);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.METAL, 1931, 2, 17, 1932, 2, 5);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.WATER, 1943, 2, 5, 1944, 1, 24);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.WOOD, 1955, 1, 24, 1956, 2, 11);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.FIRE, 1967, 2, 9, 1968, 1, 29);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.EARTH, 1979, 1, 28, 1980, 2, 15);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.METAL, 1991, 2, 15, 1992, 2, 3);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.WATER, 2003, 2, 1, 2004, 1, 21);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.WOOD, 2015, 2, 19, 2016, 2, 7);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.FIRE, 2027, 2, 6, 2028, 1, 25);
        createAndAddNewChineseYear(signYears, ChineseZodiac.GOAT, ChineseElement.EARTH, 2039, 1, 24, 2040, 2, 11);
        return signYears;
    }

    public static List<ChineseYear> getMonkeyYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.EARTH, 1908, 2, 2, 1901, 1, 21);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.METAL, 1920, 2, 20, 1921, 2, 7);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.WATER, 1932, 2, 6, 1933, 1, 25);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.WOOD, 1944, 1, 25, 1945, 2, 12);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.FIRE, 1956, 2, 12, 1957, 1, 30);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.EARTH, 1968, 1, 30, 1969, 2, 16);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.METAL, 1980, 2, 16, 1981, 2, 4);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.WATER, 1992, 2, 4, 1993, 1, 22);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.WOOD, 2004, 1, 22, 2005, 2, 8);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.FIRE, 2016, 2, 8, 2017, 1, 27);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.EARTH, 2028, 1, 26, 2029, 2, 12);
        createAndAddNewChineseYear(signYears, ChineseZodiac.MONKEY, ChineseElement.METAL, 2040, 2, 12, 2041, 1, 31);
        return signYears;
    }

    public static List<ChineseYear> getRoosterYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.EARTH, 1909, 1, 22, 1910, 2, 9);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.METAL, 1921, 2, 8, 1922, 1, 27);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.WATER, 1933, 1, 26, 1934, 2, 13);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.WOOD, 1945, 2, 13, 1946, 2, 1);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.FIRE, 1957, 1, 31, 1958, 2, 17);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.EARTH, 1969, 2, 17, 1970, 2, 5);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.METAL, 1981, 2, 5, 1982, 1, 24);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.WATER, 1993, 1, 23, 1994, 2, 9);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.WOOD, 2005, 2, 9, 2006, 1, 28);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.FIRE, 2017, 1, 28, 2018, 2, 15);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.EARTH, 2029, 2, 13, 2030, 2, 2);
        createAndAddNewChineseYear(signYears, ChineseZodiac.ROOSTER, ChineseElement.METAL, 2041, 2, 1, 2042, 1, 21);
        return signYears;
    }

    public static List<ChineseYear> getDogYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.METAL, 1910, 2, 10, 1911, 1, 29);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.WATER, 1922, 1, 28, 1923, 2, 15);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.WOOD, 1934, 2, 14, 1935, 2, 3);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.FIRE, 1946, 2, 2, 1947, 1, 21);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.EARTH, 1958, 2, 18, 1959, 2, 7);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.METAL, 1970, 2, 6, 1971, 1, 26);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.WATER, 1982, 1, 25, 1983, 2, 12);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.WOOD, 1994, 2, 10, 1995, 1, 30);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.FIRE, 2006, 1, 29, 2007, 2, 17);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.EARTH, 2018, 2, 16, 2019, 2, 4);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.METAL, 2030, 2, 3, 2031, 1, 22);
        createAndAddNewChineseYear(signYears, ChineseZodiac.DOG, ChineseElement.WATER, 2042, 1, 22, 2043, 2, 9);
        return signYears;
    }

    public static List<ChineseYear> getPigYears() {
        final List<ChineseYear> signYears = new ArrayList<>();
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.METAL, 1911, 1, 30, 1912, 2, 17);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.WATER, 1923, 2, 16, 1924, 2, 4);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.WOOD, 1935, 2, 4, 1936, 1, 23);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.FIRE, 1947, 1, 22, 1948, 2, 9);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.EARTH, 1959, 2, 8, 1960, 1, 27);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.METAL, 1971, 1, 27, 1972, 2, 14);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.WATER, 1983, 2, 13, 1984, 2, 1);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.WOOD, 1995, 1, 31, 1996, 2, 18);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.FIRE, 2007, 2, 18, 2008, 2, 6);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.EARTH, 2019, 2, 5, 2020, 1, 24);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.METAL, 2031, 1, 23, 2032, 2, 10);
        createAndAddNewChineseYear(signYears, ChineseZodiac.PIG, ChineseElement.WATER, 2043, 2, 10, 2044, 1, 29);
        return signYears;
    }
}
