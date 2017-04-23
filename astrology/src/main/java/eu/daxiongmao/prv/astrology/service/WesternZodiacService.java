package eu.daxiongmao.prv.astrology.service;

import java.time.LocalDate;

import eu.daxiongmao.prv.astrology.model.western.WesternZodiac;

/**
 * To compute the Western zodiac based on a given date.
 *
 * @author Guillaume Diaz
 *
 */
public class WesternZodiacService {

    /**
     * To get the Western zodiac that match the given birth date.
     *
     * @param birthDate
     *            birth date
     * @return the corresponding western zodiac
     */
    public static WesternZodiac getWesternZodiac(final LocalDate birthDate) {
        WesternZodiac result = null;
        for (final WesternZodiac sign : WesternZodiac.values()) {
            final LocalDate startSign = sign.getStartDate(birthDate.getYear());
            final LocalDate endSign = sign.getEndDate(birthDate.getYear());
            if (birthDate.isEqual(startSign) || birthDate.isEqual(endSign) || (birthDate.isAfter(startSign) && birthDate.isBefore(endSign))) {
                result = sign;
                break;
            }
        }
        return result;
    }

}
