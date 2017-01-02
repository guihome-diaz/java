package eu.daxiongmao.prv.astrology.service;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import eu.daxiongmao.prv.astrology.model.chinese.ChineseYear;
import eu.daxiongmao.prv.astrology.model.chinese.ChineseZodiac;

public class ChineseZodiacServiceTest {

    @Test
    public void testBirthDatePig() {
        LocalDate birthDate = LocalDate.now().withYear(1983).withMonth(11).withDayOfMonth(5);
        ChineseYear chineseYear = ChineseZodiacService.getChineseBirthYear(birthDate);
        final ChineseYear expectedYear = ChineseZodiacService.getChineseYearBySign(1983, ChineseZodiac.PIG);
        Assert.assertEquals(expectedYear, chineseYear);

        birthDate = LocalDate.now().withYear(1984).withMonth(1).withDayOfMonth(17);
        chineseYear = ChineseZodiacService.getChineseBirthYear(birthDate);
        Assert.assertEquals(expectedYear, chineseYear);
    }

    @Test
    public void testBirthDateRooster() {
        final LocalDate birthDate = LocalDate.now().withYear(1957).withMonth(11).withDayOfMonth(24);
        final ChineseYear chineseYear = ChineseZodiacService.getChineseBirthYear(birthDate);
        final ChineseYear expectedYear = ChineseZodiacService.getChineseYearBySign(1957, ChineseZodiac.ROOSTER);
        Assert.assertEquals(expectedYear, chineseYear);
    }

    @Test
    public void testBirthDateMonkey() {
        LocalDate birthDate = LocalDate.now().withYear(1956).withMonth(8).withDayOfMonth(10);
        ChineseYear chineseYear = ChineseZodiacService.getChineseBirthYear(birthDate);
        ChineseYear expectedYear = ChineseZodiacService.getChineseYearBySign(1956, ChineseZodiac.MONKEY);
        Assert.assertEquals(expectedYear, chineseYear);

        birthDate = LocalDate.now().withYear(2016).withMonth(12).withDayOfMonth(16);
        chineseYear = ChineseZodiacService.getChineseBirthYear(birthDate);
        expectedYear = ChineseZodiacService.getChineseYearBySign(2016, ChineseZodiac.MONKEY);
        Assert.assertEquals(expectedYear, chineseYear);
    }

    @Test
    public void testBirthDateGoat() {
        final LocalDate birthDate = LocalDate.now().withYear(1956).withMonth(1).withDayOfMonth(25);
        final ChineseYear chineseYear = ChineseZodiacService.getChineseBirthYear(birthDate);
        final ChineseYear expectedYear = ChineseZodiacService.getChineseYearBySign(1956, ChineseZodiac.GOAT);
        Assert.assertEquals(expectedYear, chineseYear);
    }

    @Test
    public void testBirthDateHorse() {
        final LocalDate birthDate = LocalDate.now().withYear(1954).withMonth(5).withDayOfMonth(14);
        final ChineseYear chineseYear = ChineseZodiacService.getChineseBirthYear(birthDate);
        final ChineseYear expectedYear = ChineseZodiacService.getChineseYearBySign(1954, ChineseZodiac.HORSE);
        Assert.assertEquals(expectedYear, chineseYear);
    }
}
