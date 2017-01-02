package eu.daxiongmao.prv.astrology.service;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import eu.daxiongmao.prv.astrology.model.western.WesternZodiac;

public class WesternZodiacServiceTest {

    @Test
    public void testBirthDateScorpio() {
        final LocalDate birthDate = LocalDate.now().withYear(1983).withMonth(11).withDayOfMonth(5);
        final WesternZodiac sign = WesternZodiacService.getWesternZodiac(birthDate);
        Assert.assertEquals(WesternZodiac.SCORPIO, sign);
    }

    @Test
    public void testBirthDateSagittarus() {
        LocalDate birthDate = LocalDate.now().withYear(2016).withMonth(12).withDayOfMonth(16);
        WesternZodiac sign = WesternZodiacService.getWesternZodiac(birthDate);
        Assert.assertEquals(WesternZodiac.SAGITTARUS, sign);

        birthDate = LocalDate.now().withYear(1957).withMonth(11).withDayOfMonth(24);
        sign = WesternZodiacService.getWesternZodiac(birthDate);
        Assert.assertEquals(WesternZodiac.SAGITTARUS, sign);
    }

    @Test
    public void testBirthDateCapricorn() {
        final LocalDate birthDate = LocalDate.now().withYear(1984).withMonth(1).withDayOfMonth(17);
        final WesternZodiac sign = WesternZodiacService.getWesternZodiac(birthDate);
        Assert.assertEquals(WesternZodiac.CAPRICORN, sign);
    }

    @Test
    public void testBirthDateAquarius() {
        final LocalDate birthDate = LocalDate.now().withYear(1956).withMonth(1).withDayOfMonth(25);
        final WesternZodiac sign = WesternZodiacService.getWesternZodiac(birthDate);
        Assert.assertEquals(WesternZodiac.AQUARIUS, sign);
    }

    @Test
    public void testBirthDateLeo() {
        final LocalDate birthDate = LocalDate.now().withYear(1956).withMonth(8).withDayOfMonth(10);
        final WesternZodiac sign = WesternZodiacService.getWesternZodiac(birthDate);
        Assert.assertEquals(WesternZodiac.LEO, sign);
    }

    @Test
    public void testBirthDateTaurus() {
        final LocalDate birthDate = LocalDate.now().withYear(1954).withMonth(5).withDayOfMonth(14);
        final WesternZodiac sign = WesternZodiacService.getWesternZodiac(birthDate);
        Assert.assertEquals(WesternZodiac.TAURUS, sign);
    }
}
